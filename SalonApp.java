import java.time.LocalDateTime;
import java.util.Scanner;

public class SalonApp {
    public static void main(String[] args) {
        // Creare salon
        Salon salon = new Salon();

        // Interfață simplă de text pentru interacțiune cu utilizatorul
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nMeniu:");
            System.out.println("1. Adăugare client");
            System.out.println("2. Adăugare frizer");
            System.out.println("3. Creare programare");
            System.out.println("4. Anulare programare");
            System.out.println("5. Vizualizare programări");
            System.out.println("6. Vizualizare clienți");
            System.out.println("7. Vizualizare frizeri");
            System.out.println("8. Ștergere client");
            System.out.println("9. Ștergere frizer");
            System.out.println("0. Ieșire");
            System.out.print("Alegeți o opțiune: ");
            int optiune = scanner.nextInt();
            scanner.nextLine(); // pentru a consuma newline-ul

            switch (optiune) {
                case 1:
                    // Adăugare client
                    System.out.print("Introduceți numele clientului: ");
                    String numeClient = scanner.nextLine();
                    System.out.print("Introduceți numărul de telefon: ");
                    String telefonClient = scanner.nextLine();
                    System.out.print("Preferințe (opțional): ");
                    String preferinteClient = scanner.nextLine();
                    long id = 1;

                    Client client = new Client(id, numeClient, telefonClient, preferinteClient);
                    salon.adaugaClient(client);
                    System.out.println("Client adăugat cu succes!");
                    break;

                case 2:
                    // Adăugare frizer
                    System.out.print("Introduceți numele frizerului: ");
                    String numeFrizer = scanner.nextLine();
                    System.out.print("Introduceți numărul de telefon: ");
                    String telefonFrizer = scanner.nextLine();
                    System.out.print("Introduceți tarif pentru serviciu: ");
                    double tarifFrizer = scanner.nextDouble();
                    scanner.nextLine(); // pentru a consuma newline-ul
                    System.out.print("Specializări (ex: Tuns, Retușat barbă): ");
                    String specializariFrizer = scanner.nextLine();
                    long id2 = 1;
                    Frizer frizer = new Frizer(id2, numeFrizer, telefonFrizer, tarifFrizer, specializariFrizer);
                    salon.adaugaFrizer(frizer);
                    System.out.println("Frizer adăugat cu succes!");
                    break;

                case 3:
                    // Creare programare
                    System.out.print("Alegeți clientul pentru programare (introduceți numele): ");
                    String numeClientProgramare = scanner.nextLine();
                    Client clientProgramare = (Client) salon.getClienti().get(numeClientProgramare);

                    if (clientProgramare == null) {
                        System.out.println("Clientul nu a fost găsit.");
                        break;
                    }

                    System.out.print("Alegeți frizerul (introduceți numele): ");
                    String numeFrizerProgramare = scanner.nextLine();
                    Frizer frizerProgramare = salon.getFrizeri().get(numeFrizerProgramare);

                    if (frizerProgramare == null) {
                        System.out.println("Frizerul nu a fost găsit.");
                        break;
                    }

                    System.out.print("Alegeți serviciul (ex: Tuns, Spălat): ");
                    String serviciuProgramare = scanner.nextLine();
                    System.out.print("Introduceți data și ora (ex: 2025-01-15T14:00): ");
                    String dataOraProgramareStr = scanner.nextLine();
                    LocalDateTime dataOraProgramare = LocalDateTime.parse(dataOraProgramareStr);

                    Programare programare = new Programare(clientProgramare, frizerProgramare, serviciuProgramare, dataOraProgramare);
                    salon.creeazaProgramare(programare);
                    System.out.println("Programare creată cu succes!");
                    break;

                case 4:
                    // Anulare programare
                    System.out.print("Introduceți ID-ul programării pe care doriți să o anulați: ");
                    long idProgramareDeAnulat = scanner.nextLong();
                    scanner.nextLine(); // pentru a consuma newline-ul

                    boolean succes = salon.anuleazaProgramare(idProgramareDeAnulat);
                    if (succes) {
                        System.out.println("Programarea a fost anulată cu succes!");
                    } else {
                        System.out.println("Programarea nu a putut fi anulată.");
                    }
                    break;

                case 5:
                    // Vizualizare programări
                    System.out.println("Programări:");
                    for (Programare p : salon.getProgramari()) {
                        System.out.println("Client: " + p.getClient().getNume() + ", Frizer: " + p.getFrizer().getNume() +
                                ", Serviciu: " + p.getServiciu() + ", Data: " + p.getDataOra());
                    }
                    break;

                case 6:
                    // Vizualizare clienți
                    salon.vizualizareClienti();
                    break;

                case 7:
                    // Vizualizare frizeri
                    salon.vizualizareFrizeri();
                    break;

                case 8:
                    // Ștergere client
                    System.out.print("Introduceți numele clientului de șters: ");
                    String numeClientStergere = scanner.nextLine();
                    if (!salon.getClienti().containsKey(numeClientStergere)) {
                        System.out.println("Clientul nu a fost găsit.");
                    } else {
                        salon.stergeClient(numeClientStergere);
                        System.out.println("Client șters cu succes!");
                    }
                    break;

                case 9:
                    // Ștergere frizer
                    System.out.print("Introduceți numele frizerului de șters: ");
                    String numeFrizerStergere = scanner.nextLine();
                    if (!salon.getFrizeri().containsKey(numeFrizerStergere)) {
                        System.out.println("Frizerul nu a fost găsit.");
                    } else {
                        salon.stergeFrizer(numeFrizerStergere);
                        System.out.println("Frizer șters cu succes!");
                    }
                    break;

                case 0:
                    // Ieșire
                    running = false;
                    System.out.println("La revedere!");
                    break;

                default:
                    System.out.println("Opțiune invalidă!");
            }
        }

        scanner.close();
    }
}
