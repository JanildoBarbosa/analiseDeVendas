package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Stream;

import model.entities.Sale;

public class Program {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		System.out.print("Entre o caminho do arquivo: ");		
		String path = sc.next();
		
		System.out.println();
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {

			String line = br.readLine();
			List<Sale> list = new ArrayList<>();

			while (line != null) {

				String[] fields = line.split(",");
				Integer month = Integer.parseInt(fields[0]);
				Integer year = Integer.parseInt(fields[1]);
				String seller = fields[2];
				Integer items = Integer.parseInt(fields[3]);
				Double total = Double.parseDouble(fields[4]);

				list.add(new Sale(month, year, seller, items, total));

				line = br.readLine();

			}

			System.out.println("Cinco primeiras vendas de 2016 de maior preço médio: ");

			Comparator<Sale> comparator = (s1, s2) -> s1.averagePrice().compareTo(s2.averagePrice());

			List<Sale> sales = list.stream().filter(p -> p.getYear() == 2016).sorted(comparator.reversed()).limit(5)
					.toList();

			sales.forEach(System.out::println);

			System.out.println();
			System.out.print("Valor total vendido pelo vendedor Logan nos meses 1 e 7 = ");

			double total = list.stream()
					.filter(p -> p.getSeller().equals("Logan"))
					.filter(p -> p.getMonth().equals(1) || p.getMonth().equals(7))
					.map(p -> p.getTotal())
					.reduce(0.0, (x, y) -> x + y);
					
			System.out.printf("%.2f", total);

			
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}

		sc.close();
	}

}
