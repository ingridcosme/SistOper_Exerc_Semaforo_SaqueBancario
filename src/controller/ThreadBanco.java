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

package controller;

import java.util.concurrent.Semaphore;

public class ThreadBanco extends Thread {

	private int idConta;
	private int saldoConta;
	private int valor;
	private int tipoOperacao;  //Se 0 - Deposito, se 1 - Saque
	private Semaphore semaforoSaque;
	private Semaphore semaforoDeposito;
	
	public ThreadBanco(int idConta, int saldoConta, int valor, int tipoOperacao, 
						Semaphore semaforoSaque, Semaphore semaforoDeposito) {
		this.idConta = idConta;
		this.saldoConta = saldoConta;
		this.valor = valor;
		this.tipoOperacao = tipoOperacao;
		this.semaforoSaque = semaforoSaque;
		this.semaforoDeposito = semaforoDeposito;
	}

	@Override
	public void run() {
		try {
			if(tipoOperacao == 0) {
				semaforoDeposito.acquire();
			} else {
				semaforoSaque.acquire();
			}
			calculando();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			if(tipoOperacao == 0) {
				semaforoDeposito.release();
			} else {
				semaforoSaque.release();
			}
		}
		
	}
	
	private void calculando() {
		String op = "";
		int novoSaldo = 0;
		if(tipoOperacao == 0) {
			op = "depósito";
			novoSaldo = saldoConta + valor;
		} else {
			op = "saque";
			novoSaldo = saldoConta - valor;
		}
		
		int tempo = (int)((Math.random() * 501) + 100);
		try {
			sleep(tempo);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("A conta #"+idConta+" tinha um saldo de R$ "+saldoConta
				+", que após um "+op+" no valor de R$ "+valor+" ficou com R$ "
				+novoSaldo+".");
	}
}

