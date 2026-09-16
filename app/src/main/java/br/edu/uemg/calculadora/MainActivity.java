package br.edu.uemg.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Activity principal da Calculadora.
 * Gerencia a interface de usuário, entrada de números e execução das operações matemáticas.
 */
public class MainActivity extends AppCompatActivity {

    // Índice do vetor de operandos (0 para o primeiro número, 1 para o segundo número)
    int i = 0;

    // Contador da quantidade de dígitos digitados para o número atual (máximo de 7 dígitos)
    int count = 0;

    // Vetor que armazena os dois operandos da operação [operando1, operando2]
    int[] Result;

    // Armazena o valor total resultante do cálculo realizado
    int total;

    // Limite máximo de valor válido para exibição na tela
    static int INVALID = 9999999;

    // Identificador da operação matemática selecionada ("soma", "sub", "mult", "div")
    String operador;

    // Símbolo do operador selecionado para exibição na tela ("+", "-", "X", "/")
    String operadorSimbolo = "";

    // Componentes de interface gráfica declarados no arquivo de layout XML (activity_main.xml)
    TextView RESULTSCREEN;
    Button btn00, btn01, btn02, btn03, btn04, btn05, btn06, btn07, btn08, btn09;
    Button btnSoma, btnSub, btnMult, btnDiv, btnIgual, btnLimpar;

    /*
     * Método invocado quando a Activity é criada.
     * Inicializa a tela, conecta os elementos do layout com o código Java e configura os ouvintes de clique (Listeners).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializando o vetor que armazenará os dois números da operação
        Result = new int[2];

        // VÍNCULO COM OS COMPONENTES DA TELA
        RESULTSCREEN = (TextView) findViewById(R.id.RESULTSCREEN);
        RESULTSCREEN.setTextColor(Color.BLACK); // Garante a cor do texto preta

        btn00 = (Button) findViewById(R.id.button0);
        btn01 = (Button) findViewById(R.id.button1);
        btn02 = (Button) findViewById(R.id.button2);
        btn03 = (Button) findViewById(R.id.button3);
        btn04 = (Button) findViewById(R.id.button4);
        btn05 = (Button) findViewById(R.id.button5);
        btn06 = (Button) findViewById(R.id.button6);
        btn07 = (Button) findViewById(R.id.button7);
        btn08 = (Button) findViewById(R.id.button8);
        btn09 = (Button) findViewById(R.id.button9);

        btnSoma   = (Button) findViewById(R.id.buttonSoma);
        btnSub    = (Button) findViewById(R.id.buttonSub);
        btnMult   = (Button) findViewById(R.id.buttonMult);
        btnDiv    = (Button) findViewById(R.id.buttonDiv);
        btnLimpar = (Button) findViewById(R.id.buttonLimpar);
        btnIgual  = (Button) findViewById(R.id.buttonIgual);

        // CONFIGURAÇÃO DOS EVENTOS DE CLIQUE DOS BOTÕES NUMÉRICOS (0 a 9)
        btn00.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adicionarDigito(0);
            }
        });

        btn01.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                adicionarDigito(1);
            }
        });

        btn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adicionarDigito(2);
            }
        });

        btn03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adicionarDigito(3);
            }
        });

        btn04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adicionarDigito(4);
            }
        });

        btn05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adicionarDigito(5);
            }
        });

        btn06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adicionarDigito(6);
            }
        });

        btn07.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adicionarDigito(7);
            }
        });

        btn08.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adicionarDigito(8);
            }
        });

        btn09.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adicionarDigito(9);
            }
        });

        // CONFIGURAÇÃO DOS EVENTOS DE CLIQUE DOS BOTÕES DE OPERAÇÃO (+, -, X, /)
        btnSoma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selecionarOperador("soma", "+");
            }
        });

        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selecionarOperador("sub", "-");
            }
        });

        btnMult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selecionarOperador("mult", "X");
            }
        });

        btnDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selecionarOperador("div", "/");
            }
        });

        // BOTÃO IGUAL (=): Executa o cálculo e exibe o resultado final
        btnIgual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calcular();                  // Realiza o cálculo matemático
                operador = "";               // Reseta o operador
                operadorSimbolo = "";        // Limpa o símbolo do operador para mostrar apenas o resultado
                exibirResultado();           // Mostra o resultado final na tela
                total = 0;                   // Reseta o valor total acumulado
                count = 0;                   // Reseta a contagem de dígitos
            }
        });

        // BOTÃO LIMPAR (CE): Reseta a calculadora para o estado inicial
        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limpar();
            }
        });

        // Inicializa o estado da tela ao abrir o aplicativo
        limpar();
    }

    /**
     * Adiciona um dígito ao número atual que está sendo digitado.
     * Caso um cálculo anterior tenha sido finalizado sem pressionar um operador,
     * reinicia a entrada para um novo cálculo.
     *
     * @param digito Dígito numérico (0 a 9) a ser inserido.
     */
    private void adicionarDigito(int digito) {
        // Se a operação anterior terminou (via botão '=') e o usuário digita um novo número sem selecionar operador
        if ((operador == null || operador.isEmpty()) && i == 1 && total == 0) {
            i = 0;
            Result[0] = 0;
        }

        if (count < 7) {
            Result[i] = (Result[i] * 10) + digito;
            count++;
        }
        exibirResultado();
        total = 0;
    }

    /**
     * Configura o operador da conta e atualiza a exibição da tela.
     * Caso já exista um segundo número inserido (i == 1 e count > 0), executa
     * o cálculo da operação anterior e apresenta o resultado na tela junto com o novo operador selecionado.
     *
     * @param op Nome interno da operação ("soma", "sub", "mult", "div")
     * @param simbolo Texto do operador a ser exibido na tela ("+", "-", "X", "/")
     */
    private void selecionarOperador(String op, String simbolo) {
        // Se já temos o primeiro número e o segundo número digitado, calcula a operação anterior
        if (i == 1 && count > 0) {
            calcular(); // Calcula o resultado da operação anterior (ex: 10 + 5 = 15)
        }

        operador = op;
        operadorSimbolo = simbolo;
        proximoNumero();
        exibirResultado(); // Apresenta o resultado anterior com o novo operador (ex: "15 - ")
    }

    /**
     * Atualiza o valor exibido no TextView da tela mantendo a operação corrente visível.
     * Exibe a expressão completa (ex: "10 + 5" ou "15 - "), o resultado final após o '=',
     * ou "ERROR" se houver estouro/divisão por zero.
     */
    private void exibirResultado(){
        if (total > INVALID){
            // Se o valor ultrapassar o limite permitido ou houver erro (divisão por zero), exibe ERROR
            String tela = "ERROR";
            RESULTSCREEN.setText(tela);
        } else if (operadorSimbolo != null && !operadorSimbolo.isEmpty()) {
            // Se há uma operação em andamento
            if (i == 1 && count > 0) {
                // Se o segundo número está sendo digitado (ex: "10 + 5")
                String tela = Result[0] + " " + operadorSimbolo + " " + Result[1];
                RESULTSCREEN.setText(tela);
            } else {
                // Se o operador foi selecionado para receber o próximo número (ex: "10 + " ou "15 - ")
                String tela = Result[0] + " " + operadorSimbolo + " ";
                RESULTSCREEN.setText(tela);
            }
        } else if (total != 0 && total < INVALID) {
            // Se for o resultado final do botão '=' (ex: "15")
            String tela = String.valueOf(total);
            RESULTSCREEN.setText(tela);
        } else {
            // Se ainda não iniciou uma operação (ex: "10" ou "0")
            String tela = String.valueOf(Result[i]);
            RESULTSCREEN.setText(tela);
        }
    }

    /**
     * Prepara a calculadora para receber o segundo número da operação.
     * Zera o contador de dígitos digitados e ajusta o índice do vetor para a posição 1.
     */
    private void proximoNumero(){
        count = 0; // Zera a quantidade de dígitos digitados para o novo número
        i = 1;     // Aponta o índice do vetor para a segunda posição (segundo operando)
    }

    /**
     * Reseta completamente os dados da calculadora.
     * Zera os operandos do vetor, o total acumulado, o contador de dígitos e limpa o operador selecionado.
     */
    private void limpar(){
        i = 0;                 // Retorna o índice para o primeiro operando
        Result[0] = 0;         // Zera o primeiro operando
        Result[1] = 0;         // Zera o segundo operando
        total = 0;             // Zera o valor total acumulado
        count = 0;             // Zera a contagem de dígitos digitados
        operador = "";         // Limpa o operador selecionado
        operadorSimbolo = "";  // Limpa o símbolo do operador
        exibirResultado();     // Atualiza a tela exibindo "0"
    }

    /**
     * Executa a operação aritmética matemática com base no operador selecionado ("soma", "sub", "mult", "div").
     * Armazena o resultado acumulado no primeiro elemento do vetor (`Result[0]`) para permitir cálculos encadeados.
     */
    private void calcular(){
        if (operador == null || operador.isEmpty()) return;

        switch (operador){
            case "soma":
                total = (Result[0] + Result[1]);
                break;
            case "sub":
                total = (Result[0] - Result[1]);
                break;
            case "mult":
                total = (Result[0] * Result[1]);
                break;
            case "div":
                // Valida divisão por zero
                if (Result[1] != 0) {
                    total = (Result[0] / Result[1]);
                } else {
                    total = INVALID + 1; // Força um valor acima de INVALID para exibir "ERROR" na tela
                }
                break;
        }

        // Se o resultado for válido, atualiza o primeiro número com o resultado obtido
        // para permitir operações contínuas
        if(total < INVALID){
            Result[0] = total;
            Result[1] = 0;
            i = 1;
        }
    }
}
