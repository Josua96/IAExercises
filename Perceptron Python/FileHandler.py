
import os

class FileManager:

    def __init__(self):
        self.inputs=[]
        self.results =[]
        self.weights=[]


    def addLinesFromFile(self,file,addBias):

        bits=[]
        fh = open(file,"r")
        lines = fh.read().splitlines()  #leer las líneas del archivo sin \r ó \n

        for line in lines:
            for char in line:
                bits.append(float(char))

        if addBias:
            bits.append(1)

        return bits



    def loadInputs(self,folderPath):

        files = os.listdir(folderPath)

        for fileName in files:
            self.inputs.append(self.addLinesFromFile(folderPath+"/"+fileName,True))

    def loadResults(self,filePath):

        self.results = self.addLinesFromFile(filePath,False)

    #los pesos se guardan un peso por línea en un txt que se llama weights
    def saveWeights(self, weights,fileName):

        file= open(fileName,"w")

        for weight in weights:
            file.write(str(weight)+"\n")

        file.close()

        print("Pesos guardados")

    def getWeightsFromFile(self, fileName):

        weights = []

        file = open(fileName,"r")

        lines = file.read().splitlines()  # leer las líneas del archivo sin \r ó \n

        for line in lines:
            weights.append(float(line))

        file.close()

        return weights

    def loadWeights(self,fileName):

        self.weights = self.getWeightsFromFile(fileName)
        print("Pesos cargados ....")
        print(self.weights)

    def getWeights(self):

        return self.weights

    def getInputs(self):

        return  self.inputs


    def getResults(self):

        return self.results
