package java_rush;

import java.sql.SQLException;
import java.util.Scanner;

import controllers.ApprenantController;
import controllers.PromotionController;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        ApprenantController apprenantController = new ApprenantController();
        PromotionController promotionController = new PromotionController();

        while (true) {
            System.out.println("=== Menu ===");
            System.out.println("1. Ajouter un apprenant");
            System.out.println("2. Supprimer un apprenant");
            System.out.println("3. Modifier les absences");
            System.out.println("4. Afficher tous les apprenants");
            System.out.println("5. Ajouter une promotion");
            System.out.println("6. Supprimer une promotion");
            System.out.println("7. Afficher toutes les promotions");
            System.out.println("8. Afficher les apprenants d'une promotion");
            System.out.println("9. Rechercher par promotion");
            System.out.println("10. Calculer le taux d'absentéisme");
            System.out.println("11. Quitter");

            int choix = scanner.nextInt();
            scanner.nextLine(); // Consommer la nouvelle ligne

            switch (choix) {
                case 1:
                    apprenantController.ajouterApprenant(scanner);
                    break;
                case 2:
                    apprenantController.supprimerApprenant(scanner);
                    break;
                case 3:
                    apprenantController.modifierAbsences(scanner);
                    break;
                case 4:
                    apprenantController.afficherApprenants();
                    break;
                case 5:
                    promotionController.ajouterPromotion(scanner);
                    break;
                case 6:
                    promotionController.supprimerPromotion(scanner);
                    break;
                case 7:
                    promotionController.afficherPromotions();
                    break;
                case 8:
                    promotionController.afficherApprenantsParPromotion(scanner);
                    break;
                case 9:
                    apprenantController.rechercherParPromotion(scanner);
                    break;
                case 10:
                    apprenantController.calculerTauxAbsenteisme(scanner);
                    break;
                case 11:
                    System.out.println("Au revoir !");
                    scanner.close();
                    return;
                default:
                    System.out.println("Choix invalide.");
            }
        }
    }
}
