from models.model_metric_eval import ModelMetricEval
import numpy

class ASRWEREval(ModelMetricEval):
    """
    Implementation of metric evaluation of ASR type models
    using WER
    """

    def editDistance(self, r, h):
        '''
        This function is to calculate the edit distance of reference sentence and the hypothesis sentence.
        Attributes: 
            r -> the list of words produced by splitting reference sentence.
            h -> the list of words produced by splitting hypothesis sentence.
        '''
        d = numpy.zeros((len(r)+1)*(len(h)+1), dtype=numpy.uint8).reshape((len(r)+1, len(h)+1))
        for i in range(len(r)+1):
            d[i][0] = i
        for j in range(len(h)+1):
            d[0][j] = j
        for i in range(1, len(r)+1):
            for j in range(1, len(h)+1):
                if r[i-1] == h[j-1]:
                    d[i][j] = d[i-1][j-1]
                else:
                    substitute = d[i-1][j-1] + 1
                    insert = d[i][j-1] + 1
                    delete = d[i-1][j] + 1
                    d[i][j] = min(substitute, insert, delete)
    
        result = float(d[len(r)][len(h)]) / len(r) * 100
        return result

    def asr_metric_eval(self, ground_truth, machine_translation):
        word_list_ground = []
        for sent in ground_truth:
            word_list_ground.extend(sent.split())

        word_list_hypo = []
        for sent in machine_translation:
            word_list_hypo.extend(sent.split())

        return self.editDistance(word_list_ground, word_list_hypo)