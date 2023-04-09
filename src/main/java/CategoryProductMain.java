import manager.CategoryManager;
import manager.ProductManager;
import model.Category;
import model.Product;

import java.util.List;
import java.util.Scanner;

public class CategoryProductMain {
    private static Scanner scanner = new Scanner(System.in);

    private static CategoryManager  categoryManager = new CategoryManager();
    private static ProductManager productManager = new ProductManager();

    public static void main(String[] args) {
        boolean isRun = true;

        while (isRun) {
            System.out.println("Please input 0 for exit");
            System.out.println("Please input 1 for add Category");
            System.out.println("Please input 2 for add Product");
            System.out.println("Please input 3 for update Category by id");
            System.out.println("Please input 4 for update Product by id");
            System.out.println("Please input 5 for print sum of products");
            System.out.println("Please input 6 for print max of price product");
            System.out.println("Please input 7 for print min of price product");
            System.out.println("Please input 8 for print avg of price product");
            String command = scanner.nextLine();
            switch (command) {
                case "0":
                    isRun = false;
                    break;
                case "1":
                    addCategory();
                    break;
                case "2":
                    addProduct();
                    break;
                case "3":
                    updateCategoryById();
                    break;
                case "4":
                    updateProductById();
                    break;
                case "5":
                    printSumOfProducts();
                    break;
                case "6":
                    printMaxOfPriceProduct();
                    break;
                case "7":
                    printMinOfPriceProduct();
                    break;
                case "8":
                    printAvgOfPriceProduct();
                    break;
                default:
                    System.out.println("Invalid Command");
            }
        }
    }

    private static void addCategory() {
        System.out.println("Please input category name");
        String categoryStr = scanner.nextLine();
        String[] categoryData = categoryStr.split(",");
        Category category = new Category();
        category.setName(categoryData[0]);
        categoryManager.save(category);
    }

    private static void addProduct() {
        List<Category> all = categoryManager.getAll();
        for (Category category : all) {
            System.out.println(category);
        }
        System.out.println("Please choose category id");
        int id = Integer.parseInt(scanner.nextLine());
        Category category = categoryManager.getById(id);
        if (category != null) {
            System.out.println("Please input product name,description,price,quantity");
            String productStr = scanner.nextLine();
            String[] productData = productStr.split(",");

            Product product = new Product();
            product.setCategory(category);
            product.setName(productData[0]);
            product.setDescription(productData[1]);
            product.setPrice(Integer.parseInt(productData[2]));
            product.setQuantity(Integer.parseInt(productData[3]));
            productManager.save(product);
        }
    }

    private static void updateCategoryById() {
        List<Category> all = categoryManager.getAll();
        for (Category category : all) {
            System.out.println(category);
        }
        System.out.println("Please choose category id");
        int id = Integer.parseInt(scanner.nextLine());
        if (categoryManager.getById(id) != null) {
            System.out.println("Please input category name");
            String categoryStr = scanner.nextLine();
            String[] categoryData = categoryStr.split(",");
            Category category = new Category();
            category.setId(id);
            category.setName(categoryData[0]);
            categoryManager.update(category);
            System.out.println("Category was updated!");
        } else {
            System.out.println("Category does not exists");
        }
    }

    private static void updateProductById() {
    }

    private static void printSumOfProducts() {
    }

    private static void printMaxOfPriceProduct() {
    }

    private static void printMinOfPriceProduct() {
    }

    private static void printAvgOfPriceProduct() {
    }
}
