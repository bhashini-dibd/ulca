import logging
from logging.config import dictConfig
from models.model_metric_eval import ModelMetricEval
from datasets import load_metric
import numpy as np
import codecs
import sys
from .transliteration_cer_eval import TransliterationCEREval

log = logging.getLogger('file')
levenshtein = TransliterationCEREval.transliteration_metric_eval

class TransliterationAccuracyEval(ModelMetricEval):
    """
    Implementation of metric evaluation of Transliteration type models
    using Accuracy, f-score, char-acc, error_words
    """

    #def __init__(self):
    #    self.cer_score = load_metric('cer', revision="master")
    def charerr(self,ref,hyp):
        _, (s, i, d) = levenshtein(ref, hyp)
        cer_s = s
        cer_i = i
        cer_d = d
        cer_n = len(ref)

        if cer_n > 0:
            return (cer_s + cer_i + cer_d) / cer_n
        else:
            return 0

    def inverse_rank(self,candidates, reference):
        '''
        Returns inverse rank of the matching candidate given the reference
        Returns 0 if no match was found.
        '''
        rank = 0
        while (rank < len(candidates)) and (candidates[rank] != reference):
            rank += 1
        if rank == len(candidates):
            return 0.0
        else:
            return 1.0/(rank+1)

    def LCS_length(self,s1, s2):
        '''
        Calculates the length of the longest common subsequence of s1 and s2
        s1 and s2 must be anything iterable
        The implementation is almost copy-pasted from Wikibooks.org
        '''
        m = len(s1)
        n = len(s2)
        # An (m+1) times (n+1) matrix
        C = [[0] * (n+1) for i in range(m+1)]
        for i in range(1, m+1):
            for j in range(1, n+1):
                if s1[i-1] == s2[j-1]: 
                    C[i][j] = C[i-1][j-1] + 1
                else:
                    C[i][j] = max(C[i][j-1], C[i-1][j])
        return C[m][n]

    def f_score(self,candidate, references):
        '''
        Calculates F-score for the candidate and its best matching reference
        Returns F-score and best matching reference
        '''
        # determine the best matching reference (the one with the shortest ED)
        best_ref = references[0]
        best_ref_lcs = self.LCS_length(candidate, references[0])
        for ref in references[1:]:
            lcs = self.LCS_length(candidate, ref)
            if (len(ref) - 2*lcs) < (len(best_ref) - 2*best_ref_lcs):
                best_ref = ref
                best_ref_lcs = lcs
        
        #try:
        precision = float(best_ref_lcs)/float(len(candidate))
        recall = float(best_ref_lcs)/float(len(best_ref))
        #except:
        #    import ipdb
        #    ipdb.set_trace()
        
        if best_ref_lcs:
            return 2*precision*recall/(precision+recall), best_ref
        else:
            return 0.0, best_ref

    def mean_average_precision(self,candidates, references, n):
        '''
        Calculates mean average precision up to n candidates.
        '''
        
        total = 0.0
        num_correct = 0
        for k in range(n):
            if k < len(candidates) and (candidates[k] in references):
                num_correct += 1
            total += float(num_correct)/float(k+1)
            
        return total/float(n)

    def transliteration_metric_eval(self, ground_truth, machine_translation):

        try:
            '''
            Evaluates all metrics to save looping over input_data several times
            n is the map-n parameter
            Returns acc, f_score, mrr, map_ref, map_n
            '''
            mrr = {}
            acc = {}
            f = {}
            f_best_match = {}
            #map_n = {}
            map_ref = {}
            #map_sys = {}
            acc_10 = {}
            
            #added by yash
            characc ={}
            error_words = {}
            correct_words = {}
            
            stderr = codecs.getwriter('utf-8')(sys.stderr)
            
            for src_word in list(ground_truth.keys()):
                if src_word in machine_translation:
                    candidates = machine_translation[src_word]
                    references = ground_truth[src_word]
                    
                    acc[src_word] = max([int(candidates[0] == ref) for ref in references]) # either 1 or 0
                    
                    if acc[src_word]==0:
                        error_words[src_word] = {'ref': references, 'candidates' : candidates }
                    if acc[src_word]==1:    
                        correct_words[src_word] = {'ref': references, 'candidates' : candidates }

                    f[src_word], f_best_match[src_word] = self.f_score(candidates[0], references)
                    
                    mrr[src_word] = max([self.inverse_rank(candidates, ref) for ref in references])
                    
                    #map_n[src_word] = mean_average_precision(candidates, references, n)
                    map_ref[src_word] = self.mean_average_precision(candidates, references, len(references))
                    #map_sys[src_word] = mean_average_precision(candidates, references, len(candidates))
                    
                    ## compute accuracy at 10- Anoop
                    acc_10[src_word] = max([int(ref in candidates) for ref in references]) # either 1 or 0
                    
                    # compute char accuracy
                    characc[src_word] = 1 - self.charerr(references[0], candidates[0])

                else:
                    #stderr.write('Warning: No transliterations found for word %s\n' % src_word)
                    print('No transliterations')
                    mrr[src_word] = 0.0
                    acc[src_word] = 0.0
                    f[src_word] = 0.0
                    f_best_match[src_word] = ''
                    #map_n[src_word] = 0.0
                    map_ref[src_word] = 0.0
                    #map_sys[src_word] = 0.0
                    # Anoop
                    acc_10[src_word]=0.0
                    characc[src_word] = 0.0

            return acc, f, f_best_match, mrr, map_ref, acc_10, characc, error_words, correct_words 
        except Exception as e:
            log.exception(f"Exception in calculating CER: {str(e)}")
            return None

# Log config
dictConfig({
    'version': 1,
    'formatters': {'default': {
        'format': '[%(asctime)s] {%(filename)s:%(lineno)d} %(threadName)s %(levelname)s in %(module)s: %(message)s',
    }},
    'handlers': {
        'info': {
            'class': 'logging.FileHandler',
            'level': 'DEBUG',
            'formatter': 'default',
            'filename': 'info.log'
        },
        'console': {
            'class': 'logging.StreamHandler',
            'level': 'DEBUG',
            'formatter': 'default',
            'stream': 'ext://sys.stdout',
        }
    },
    'loggers': {
        'file': {
            'level': 'DEBUG',
            'handlers': ['info', 'console'],
            'propagate': ''
        }
    },
    'root': {
        'level': 'DEBUG',
        'handlers': ['info', 'console']
    }
})