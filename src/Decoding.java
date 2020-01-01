import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Decoding {

    char[] arr_ru = {'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н',
            'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ь', 'ы', 'ъ', 'э', 'ю', 'я'};

    char [] arr_frequency = {'ё','ъ','э','ф','ц','щ','х','ю','ж','й','ч','ш','г','б','з','ы','ь','я','д','м','к','у','п',
            'в','л','р','т','с','н','и','е','а','о'};

    char [] arr_frequency2 = {'ё','ъ', 'ф', 'щ','ц','э','ю','х','ш','й','ж','з','ч','б','г','ы','ь','я','п',
            'у','м','д','к','р','в','л','с','т','и','н','а','е','о'};

    char [] arr_frequency3 = {'ё','ъ','ф','э','щ','ц','ю','ш','ж','х','й','ч','б','з','г','ь','ы','я','у','п',
            'д','м','к','л','в','р','с','т','н','и','a','е','о'}; //массив на основе таблицы частотности в порядке возрастания

    char [] arr_frequency4 = {'ё','ъ','ф','э','щ','ц','ю','х','ш','ж','й','ч','з','ы','г','ь','я','б','п',
            'у','к','м','д','в','р','л','с','н','т','и','е','а','о'};

    char [] arr_frequency5 = {'ё','ъ','ф','э','ц','щ','ю','х','ш','ж','й','б','ч','з','ы','г','ь','я','д','п',
            'у','к','м','в','р','л','с','н','т','и','е','а','о'};

    public static void main(String[] args) {
        System.out.println("Внимание! Документ может быть расшифрован не с первого раза, в слечае неудачи с расшифровкой нажмите 2,");
        System.out.println("после этого программа произведёт расшифровку, используя другой алфавит частотности");
        System.out.println("Прогграмма не расшифровывает большие буквы, пробелы и знаки припенания");
        System.out.println("Программа работает тоько с символами русского алфавита");
        Decoding user = new Decoding();
        user.control();
    }

    public void control(){ // метод, управляющий выбором алфавита для расшифровки
        Scanner scanner = new Scanner(System.in);

        go(this.arr_frequency);
        System.out.println();
        System.out.println("Если документ расшифрован, нажмите 1, если нет, нажмите 2");
        int a = scanner.nextInt();
        if (a==2){
            go(this.arr_frequency2);
            System.out.println();
            System.out.println("Если документ расшифрован, нажмите 1, если нет, нажмите 2");
            a = scanner.nextInt();
            if (a==2) {
                go(this.arr_frequency3);
                System.out.println();
                System.out.println("Если документ расшифрован, нажмите 1, если нет, нажмите 2");
                a = scanner.nextInt();
                if (a==2) {
                    go(this.arr_frequency4);
                }
            }
        }

    }

    public void go(char [] arr_frequency) { // основной метод, в котором происходит работа с зашифрованным файлом

        String text = "";
        Scanner in = null;
        try {
            in = new Scanner(new File("d://LetterFrequencyDecoding/cipher.txt")); // убедитесь в том, что путь к файлу указан привильно
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while(in.hasNext())
            text += in.nextLine() + "\r\n";
        in.close();

        char[] textMass = text.toCharArray(); //превращение текста в массив char


        int[] arrNum = new int[arr_ru.length]; //массив, хранящий колличество каждой буквы в тексте


        for (int i = 0; i < arr_ru.length; i++) { // определяем, сколько раз каждая буква повторяется в тексте
            int num=0;
            char letter=arr_ru[i];
            for (int j = 0; j < textMass.length; j++) {
                if (textMass[j]==letter){
                    num++;
                }
            }
            arrNum[i]=num;
        }

        for (int i =0;i<arrNum.length;i++){ //проверка на то, что всех букв содержится разное колличество
            for (int j =0;j<arrNum.length;j++){
                if(arrNum[i]==arrNum[j]){
                    arrNum[i]++; //если это не так, добавляем 1 к значению колличества одной из букв, для того, чтобы одна буква не повторилась 2 раза
                }
            }
        }

        char [] b = sorting(arr_ru,arrNum); //сортировка букв в порядке возрастания их колличества в тексте


        char [] newText = new char[textMass.length]; // массив для расшифрованного текста

        for (int i =0;i<newText.length;i++){ //добавление в новый массив содержимого зашифровонного текста
            newText[i]=textMass[i];
        }

        ArrayList list = new ArrayList(); // переменная, в которой будут хранится номера конкретных букв

        int letterNum = 0; // счётчик букв

        System.out.println("Частотность букв в данном шифре в порядке возрастаная: ");

        for (int i =0;i<arr_ru.length;i++){ //нужно заменить все буквы

            list.clear(); // очистить список
            for (int k =0;k<newText.length;k++){
                if (textMass[k]==b[i]){
                    list.add(k); //добавляем все номера конкретной буквы

                }
            }
            System.out.print(b[i] + " ");

            for (int j =0;j<newText.length;j++){
                for (int newListNum=0; newListNum<list.size();newListNum++){
                    if (list.get(newListNum).equals(j)){ //если номер буквы совпадает с номером в списке
                        newText[j]=arr_frequency[letterNum]; // заменяем эту букву, на соответствующую из таблицы частотности
                        break;
                    }
                }
            }
            letterNum++;
        }

        System.out.println();

        for (int i = 0; i<newText.length;i++){ //вывод результата
            System.out.print(newText[i]);
            if (i%100==0&&i!=0){
                System.out.println();
            }
        }
    }

    public char[] sorting(char[] a, int[] num) { //сортировка букв в порядке возрастания их колличества в тексте
        // присваиваем массив с колличеством разных букв
        int[] noSortNum = new int[33];

        for (int i =0;i<noSortNum.length;i++){
            noSortNum[i]=num[i];
        }

        int[] newNum = sort(num);

        char[] b = new char[33];

        for (int i =0;i<noSortNum.length;i++){
            b[i] = a[i];
        }

        for (int i = 0; i < noSortNum.length; i++) {
            for (int j = 0; j < newNum.length; j++) {
                if (newNum[j] == noSortNum[i]) {
                    b[j] = a[i];
                }
            }
        }

        return b;
    }

    public int[] sort(int[] num2){ //метод для сортировки колличества букв

        int [] newNum = num2;

        for (int i = 0; i < newNum.length - 1; i++) {
            for (int j = 0; j < newNum.length - 1; j++) {
                if (newNum[j] > newNum[j + 1]) {
                    int temp = newNum[j];
                    newNum[j] = newNum[j + 1];
                    newNum[j + 1] = temp;
                }
            }
        }
        return newNum;
    }
}