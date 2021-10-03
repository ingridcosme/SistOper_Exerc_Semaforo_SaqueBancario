/*
 * Um banco deve controlar Saques e Dep�sitos.
 * O sistema pode permitir um Saque e um Dep�sito Simult�neos, mas nunca 2 Saques
 * ou 2 Dep�sitos Simult�neos.
 * Para calcular a transa��o (Saque ou Dep�sito), o m�todo deve receber o c�digo
 * da conta, o saldo da conta e o valor a ser transacionado.
 * Deve-se montar um sistema que deve considerar que 20 transa��es simult�neas
 * ser�o enviadas ao sistema (aleatoriamente essas transa��es podem ser qualquer
 * uma das op��es) e tratar todas as transa��es, de acordo com as regras acima.
 */

package view;

import java.util.concurrent.Semaphore;

import controller.ThreadBanco;

public class Sistema {

	public static void main(String[] args) {
		Semaphore semaforoSaque = new Semaphore(1);
		Semaphore semaforoDeposito = new Semaphore(1);
		
		for(int idConta = 1; idConta <= 20; idConta++) {
			int saldoConta = (int)((Math.random() * 9991) + 10);
			int valor = (int)((Math.random() * 991) + 10);
			int tipoOperacao = (int)(Math.random() * 2);  //Se 0 - Deposito, se 1 - Saque
			
			Thread tBanco = new ThreadBanco(idConta, saldoConta, valor, 
								tipoOperacao, semaforoSaque, semaforoDeposito);
			tBanco.start();
		}
	}

}
