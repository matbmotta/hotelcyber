package br.edu.up.sistema;

import java.text.ParseException;
import java.util.Scanner;
import br.edu.up.util.Case;
import br.edu.up.util.cls;

public class Programa {
	public static void main(String[] args) throws ParseException {
		Scanner leitor = new Scanner(System.in);
		char Escolha = 0;
		int cont = 0;
		
		do {	
			System.out.println("-------------------Gerencimento dos Hóspedes------------------");
			System.out.println("Escolha a opção que deseja: ");
			System.out.println("(1) Reserva de Quarto");
			System.out.println("(2) Check in");
			System.out.println("(3) Consumo do Hóspede");
			System.out.println("(4) Check out");
			int op = leitor.nextInt();
			
			switch(op) {
				case 1:
					cls.limpar();
					Case.reserva(leitor);
				break;
				
				case 2:
					cls.limpar();
					Case.Checkin(leitor);
				break;
				
				case 3:
					cls.limpar();
					Case.Consumo(leitor);
				break;
				
				case 4:
					cls.limpar();
					Case.CheckOut(leitor);
				break;
				
				default:
					System.out.println("Escolha inválida");
			}

			do {
				cont = 0;
				System.out.println("Deseja continuar no programa? (S)Sim (N)Não");
				Escolha = leitor.next().charAt(0);
				if (Escolha == 'S' || Escolha == 's'|| Escolha == 'N' || Escolha == 'n') {
					cont = 1;
					cls.limpar();
				}else{
					System.out.println("Opção invalida, escolha novamente.");
				}
			}while(cont == 0);
		}while(Escolha == 'S' || Escolha == 's');
		leitor.close();
	}
}