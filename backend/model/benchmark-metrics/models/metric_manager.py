from configs.configs import metric_config_path
import json
import os

import metrics as metric_pkg

class MetricManager:

    __m_inst = None

    def __init__(self):
        if MetricManager.__m_inst != None:
            raise Exception("Singleton class")
        else:
            MetricManager.__m_inst = self

    @staticmethod
    def getInstance():
        if MetricManager.__m_inst == None:
            MetricManager()

        return MetricManager.__m_inst

    @property
    def metric_map(self):
        return self._metric_map

    @metric_map.setter
    def metric_map(self, value):
        self._metric_map = value

    def get_metric_list(self, filepath):
        with open(filepath) as m_file:
            metric_list = json.loads(m_file.read())
            return metric_list

    def load_metrics(self):
        self.metric_map = {}
        m_filepath = os.path.abspath(os.path.join(os.curdir, metric_config_path))
        metric_list = self.get_metric_list(m_filepath)
        for metric in metric_list:
            m_class = vars(metric_pkg)[metric["metric_implementation"]]
            m_inst = m_class()
            self.metric_map[metric["metric_name"]] = m_inst

    def get_metric_execute(self, metric):
        if metric in self.metric_map.keys():
            return self.metric_map[metric]

        return None






