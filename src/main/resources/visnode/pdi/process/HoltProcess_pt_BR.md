# Holt

O algoritmo de Holt et al. (1987) utiliza uma vizinhança 3 x 3 para a análise
dos pixels a serem removidos. O formato da matriz permite que seja feita uma análise do
pixel central C e seus vizinhos NO, N, NE, O, L, SO, S e SE.

O algoritmo possui duas funções que são definidas por:
* v(): Retorna verdadeiro se o valor do ponto for o mesmo valor do objeto (valor preto) e falso se o valor do ponto for igual ao valor do plano de fundo (valor branco)
* edge(): Retorna verdadeiro se o valor processado estiver na borda do objeto e falso
se não estiver

    Um pixel está na borda quando sua conectividade é igual a 1 e quando possuirde 2 a 6 vizinhos conectados.

# ![Mask](https://raw.githubusercontent.com/VISNode/VISNode/master/src/main/resources/visnode/pdi/process/image/HoltProcessMask.png)

#### Parâmetros
* __image:__ Imagem

#### Saída
* __image:__ Imagem
