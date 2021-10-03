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
			op = "dep�sito";
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
				+", que ap�s um "+op+" no valor de R$ "+valor+" ficou com R$ "
				+novoSaldo+".");
	}
}

