# Frei Chen

O operador Frei-Chein utiliza um conjunto de nove máscaras de tamanho 3 x 3. As máscara M1 a M4 são utilizadas para realizar a detecção de bordas, já a M5 a M8 realizam a detecção de retas, e a M9 representa a média dos pixels na região da matriz (PEDRINI; SCHWARTZ, 2008).

\[
M1
\begin{bmatrix}
     	 	 	 1 &  \sqrt{2} &  1         \\
	             0 &         0 &  0         \\
	            -1 & -\sqrt{2} & -1         \\
\end{bmatrix}
M2
\begin{bmatrix}
     	     0 &             1 & -1         \\
	  \sqrt{2} &             0 & -\sqrt{2}  \\
	         1 &             0 & -1         \\
\end{bmatrix}
M3
\begin{bmatrix}
     	 	 0 &            -1 & \sqrt{2}   \\
	         1 &             0 & -1         \\
	 -\sqrt{2} &             1 &  0         \\
\end{bmatrix}
\]

\[
M4
\begin{bmatrix}
      \sqrt{2} &            -1 &  0         \\
	        -1 &             0 & 1          \\
	         0 &             1 & -\sqrt{2}  \\
\end{bmatrix}
M5
\begin{bmatrix}
    	 	 0 &             1 &  0          \\
	        -1 &             0 & -1          \\
	         0 &             1 &  0          \\
\end{bmatrix}
M6
\begin{bmatrix}
  	 	 	-1 &             0 &  1          \\
             0 &             0 &  0          \\
             1 &             0 & -1          \\
\end{bmatrix}
\]

\[
M7
\begin{bmatrix}
            1 &            -2 &  1           \\
	       -2 &             4 & -2           \\
	        1 &            -2 &  1           \\
\end{bmatrix}
M8
\begin{bmatrix}
           -2 &             1 & -2            \\
	        1 &             4 &  1            \\
	       -2 &             1 & -2            \\
\end{bmatrix}
M9
\begin{bmatrix}
     	 	 1 &             1 &  1           \\
	         1 &             1 &  0           \\
	         1 &             1 & -1           \\
\end{bmatrix}
\]



#### Parâmetros
* __image:__ Imagem

#### Saída
* __image:__ Imagem
