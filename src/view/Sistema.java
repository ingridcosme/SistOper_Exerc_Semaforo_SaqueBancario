/*
 * Um banco deve controlar Saques e Depósitos.
 * O sistema pode permitir um Saque e um Depósito Simultâneos, mas nunca 2 Saques
 * ou 2 Depósitos Simultâneos.
 * Para calcular a transação (Saque ou Depósito), o método deve receber o código
 * da conta, o saldo da conta e o valor a ser transacionado.
 * Deve-se montar um sistema que deve considerar que 20 transações simultâneas
 * serão enviadas ao sistema (aleatoriamente essas transações podem ser qualquer
 * uma das opções) e tratar todas as transações, de acordo com as regras acima.
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
