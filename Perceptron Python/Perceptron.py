import random


class Perceptron:

    def __init__(self,inputsLength):
        self.pesos=[]
        self.banderasEntrada= []
        self.alfa=1
        self.inicializarPesos(inputsLength)
        

    def inicializarBanderas(self,amount):
        
        for i in range(0,amount,1):
            self.banderasEntrada.append(False)

    def inicializarPesos(self,amount):

        for i in range(0,amount,1):
            #redondear y establecer rangos para el random
            self.pesos.append(round(random.uniform(-2.0,2.0),2))

    def calcularNet(self,listaEntradas):
        limite= len(listaEntradas)
        net=0
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
        print("Valor de net = " + str(net))
        print("Valor de salida = " + str(self.funcionEscalon(net)))
        print("")
        return net
            
    def entrenar(self, entradas,resultados):

        noConverge=True
        cantidadEntradas= len(entradas)-1
        self.inicializarBanderas(cantidadEntradas+1)
        
        while False in self.banderasEntrada:
            
            entradaAleatoria = random.randint(0,cantidadEntradas)

            if self.banderasEntrada[entradaAleatoria] == True:
                continue

            
            net = self.calcularNet(entradas[entradaAleatoria])

            salida = self.funcionEscalon(net)

            error = resultados [entradaAleatoria] - salida
            print("patron ")
            print(entradas[entradaAleatoria])
            print("NET = "+ str(net))
            print("Salida = "+ str(salida))
            print ("Error = "+ str(error))
            
            if error < 0 or error > 0:
                print("Actuailzando pesos")
                self.actualizarPesos(resultados [entradaAleatoria],salida,entradas[entradaAleatoria])
                self.actualizarBanderas(False)
            else:
                
                self.banderasEntrada[entradaAleatoria]= True

            print(" ")
            
        

    
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


