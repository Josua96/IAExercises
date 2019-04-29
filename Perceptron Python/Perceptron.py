import sys
sys.path.append('../')
import argparse
import os


from FileHandler import FileManager

import random


class Perceptron:

    def __init__(self,inputsLength,initWeights):
        self.pesos=[]
        self.entradas=[]
        self.resultados=[]
        self.banderasEntrada= []
        self.alfa=1
        if initWeights:
            self.inicializarPesos(inputsLength)

    def getPesos(self):

        return self.pesos

    def setPesos(self,pesos):
        self.pesos= pesos

    def setDatosEntrenamiento(self,entradas,resultados):
        self.entradas=entradas
        self.resultados= resultados
        print("entradas")
        print(self.entradas)
        print("Resultados")
        print(self.resultados)

    def inicializarBanderas(self,amount):
        
        for i in range(0,amount,1):
            self.banderasEntrada.append(False)

    def inicializarPesos(self,amount):

        for i in range(0,amount,1):
            #redondear y establecer rangos para el random
            self.pesos.append(round(random.uniform(-2.0,2.0),2))

        print("Pesos inicializados ... ")
        print(self.pesos)

    def calcularNet(self,listaEntradas):
        limite= len(listaEntradas)
        net=0
        print("Calculando net")
        print(listaEntradas)
        print(self.pesos)

        for i in range (0,limite,1):
            net += self.pesos[i] * listaEntradas[i]

        return net

    def actualizarPesos(self,deseado,obtenido,entrada):
        limite = len(self.pesos)

        for i in range (0,limite,1):
            self.pesos[i]= self.pesos[i] + (self.alfa * (deseado - obtenido) * entrada[i])

    def funcionEscalon(self,valorNet):

        if valorNet >= 0 :
            return 1
        else:
            return 0

    def actualizarBanderas(self,valor):

        limite= len(self.banderasEntrada)
        for i in range(0,limite,1):
            self.banderasEntrada[i] = valor

    def validar(self,entrada):
        print("")
        print("Para patron")
        print(entrada)
        net = self.calcularNet(entrada)
        salida = self.funcionEscalon(net)
        print("Valor de net = " + str(net))
        print("Valor de salida = " + str(salida))
        print("")
        return salida
            
    def entrenar(self):

        cantidadEntradas= len(self.entradas)-1
        print("Cantidad de entradas ... " + str(cantidadEntradas))
        self.inicializarBanderas(cantidadEntradas+1)
        
        while False in self.banderasEntrada:
            
            entradaAleatoria = random.randint(0,cantidadEntradas)

            if self.banderasEntrada[entradaAleatoria] == True:
                continue

            
            net = self.calcularNet(self.entradas[entradaAleatoria])

            salida = self.funcionEscalon(net)

            error = self.resultados [entradaAleatoria] - salida
            print("patron ")
            print(self.entradas[entradaAleatoria])
            print("NET = "+ str(net))
            print("Salida = "+ str(salida))
            print ("Error = "+ str(error))
            
            if error < 0 or error > 0:
                print("Actuailzando pesos")
                self.actualizarPesos(self.resultados[entradaAleatoria],salida,self.entradas[entradaAleatoria])
                self.actualizarBanderas(False)
            else:
                
                self.banderasEntrada[entradaAleatoria]= True

            print(" ")

        print("Entrenamiento finalizados ............... ")


weightsFileName= "Weights.txt"
fm = FileManager()
if __name__ == '__main__':

    # para el caso del perceptron se usará solo un archivo para resultados por que la salida siempre es binaria cada
    ## línea representa el resultado de una entrada respectiva
    # Argumentos para entrenar -> load -> 0 cargar pesos 1 no cargar T: 0->sí 1=no, inputs: ruta a entradas. results ruta al archivo donde están los resultados
    parser = argparse.ArgumentParser(description='Manage params')

    parser.add_argument('--load', action="store", dest="load", required=True)
    parser.add_argument('--train', action="store", dest="train", required=True)
    parser.add_argument('--inputs', action="store", dest="inputs", required=True)
    parser.add_argument('--results', action="store", dest="results", required=False)

    given_args = parser.parse_args()

    if(int(given_args.train)== 0):
        #entrenar perceptron
        fm.loadInputs(given_args.inputs)
        fm.loadResults(given_args.results)
        initWeights = True
        weights =[]

        if (int(given_args.load)==0):
            initWeights=False
            fm.loadWeights(weightsFileName)
            weights = fm.getWeights()


        perceptron = Perceptron(len(fm.getInputs()[0]), initWeights)

        #asignar los pesos si se deben cargar
        if ( len(weights) > 0):
            perceptron.setPesos(weights)

        print("Datos cargados----------")
        print(fm.getInputs())
        print(fm.getResults())
        perceptron.setDatosEntrenamiento(fm.getInputs(),fm.getResults())
        perceptron.entrenar()

        print("guardando resultados del entrenamiento")

        fm.saveWeights(perceptron.getPesos(),weightsFileName)

        print("validando ")
        perceptron.validar(fm.getInputs()[0])
        perceptron.validar(fm.getInputs()[1])
        perceptron.validar(fm.getInputs()[2])
        perceptron.validar(fm.getInputs()[3])

        #guardar los pesos


    else:

        # se asume que siempre se valida con una entrada y siempre se cargan los pesos

        fm.loadInputs(given_args.inputs)
        fm.loadWeights(weightsFileName)
        perceptron = Perceptron(len(fm.getInputs()[0]), False)
        perceptron.setPesos(fm.getWeights())
        perceptron.validar(fm.getInputs()[0])

        print()
        # validar la entrada


"""""""""""  Primer prueba 
trainingData= [[1,0,0],[1,0,1],[1,1,0],[1,1,1]]

#  and results= [0,0,0,1]
# or results= [0,1,1,1]

results= [0,1,1,1]

perceptron = Perceptron(len(trainingData[0]))

perceptron.entrenar(trainingData, results)
print(" ")
print("******************************************************************")
print(" ")
print("Validando ...")
print(" ")
print("******************************************************************")
perceptron.validar(trainingData[0])
"""""""""""

