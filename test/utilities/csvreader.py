import csv, sys
class CSVreader:

    def read_data(location):
        raw_data = []
        filename = location
        with open(filename, mode='r') as f:
            csv_reader = csv.DictReader(f)
            for row in csv_reader:
                raw_data.append(row)
                # print (row)
                # return row
        return raw_data


if __name__ == "__main__":
    obj = CSVreader()
    obj.read_data()