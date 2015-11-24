import sys
import matplotlib.pyplot as plt

# parsing data from text files

def parse(inputfile):
    indices = [];
    values = [];
    for line in inputfile:
        indices.append(line.split(" ")[0].strip())
        values.append(line.split(" ")[1].strip())
    return indices,values

def prepare():
    plt.figure().patch.set_facecolor('white')
    plt.ylabel('Average Steps to Reach Goal')
    plt.xlabel('Trial Number')
    plt.title("Average Steps vs Trials for Learning Algorithms")

def plot(argv):
    prepare()
    names = [];
    for arg in argv[1:]:
        datFile = open(arg)
        parsed = parse(datFile)
        plt.plot(parsed[0],parsed[1])
        names.append(arg.split(".")[0])
        datFile.close()
    plt.legend(names)
    plt.show()
    return names



plot(sys.argv)
