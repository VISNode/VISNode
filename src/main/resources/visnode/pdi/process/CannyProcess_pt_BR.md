# Canny

O operador de Canny (1986) propõem um método que tem como objetivo otimizar a detecção de bordas em uma imagem que possui ruídos. Este método é o mais complexo,mas geralmente tem resultados superiores aos demais métodos com a mesma finalidade. Esta técnica é dividida em etapas. Inicialmente a imagem é suavizada através a aplicação de um filtro Gaussiano. Após esta suavização, são calculadas a magnitude e a direção do gradiente. Após o cálculo do gradiente, a borda é encontrada utilizando apenas os pontos onde a magnitude seja máxima na direção do gradiente, reduzindo assim, a espessura da borda (PEDRINI; SCHWARTZ, 2008).

A após a identificação da borda, é possível que a imagem ainda contenha certos fragmentos causadores de ruído. Para solucionar este problema, o operador de Canny (1986) faz uso de dois limiares T 1 e T 2 , constituindo a etapa denominada limiarização com histerese. Desta forma, os pontos da borda que possuem gradiente maiores que T 2 são mantidos na imagem. Pontos que estão conectados a estes pontos e que possuem magnitude de gradiente maior que T1 também são considerados como pertencentes a borda (PEDRINI; SCHWARTZ, 2008).

#### Parâmetros
* __image:__ Imagem original

#### Saída
* __image:__ Imagem com bordas detectadas
