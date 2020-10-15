import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class knn {

    public static String Classificar(File B, List<Double> X, int k) throws FileNotFoundException{
        Double distancia;
        String[] sampleSplited;
        List<Double> P;
        List<String> sampleTrain;
        String classeTrain, E;
        Exemplo exemplo;
        List<Exemplo> exemplosList;
        List<Exemplo> ordeneredList;
        List<Exemplo> kList;

        int lastIndex;

        Scanner baseScanner = null;
        
        try {

            exemplosList = new ArrayList<Exemplo>();
            baseScanner = new Scanner(B);

            while (baseScanner.hasNextLine()) {
                E = baseScanner.nextLine();

                sampleSplited = E.split(",");

                sampleTrain = new LinkedList<String>(Arrays.asList(sampleSplited));

                lastIndex = sampleTrain.size() - 1;

                //classeTrain = sampleTrain.get(lastIndex);

                classeTrain = sampleTrain.remove(lastIndex);

                P = new ArrayList<Double>(Helpers.stringListToDouble(sampleTrain));

                distancia = Helpers.calculaDistancia(P, X);

                exemplo = new Exemplo(classeTrain, distancia);

                exemplosList.add(exemplo);
            }

            Collections.sort(exemplosList);

            
            ordeneredList = new ArrayList<Exemplo>(exemplosList);

            kList = new ArrayList<Exemplo>(ordeneredList.subList(0, k)); 

            return classeMaisFrequente(kList);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Ocorreu um problema ao classificar o exemplo.\n" + e.getMessage());
            throw e;

        } finally{
            baseScanner.close();
        }
    }

    private static String classeMaisFrequente(List<Exemplo> List) {
        List<String> classes = new ArrayList<>();
        List<String> classesMaisFrequentes = new ArrayList<>();
        int counter = 0, curr_frequency;
        String classeFrequente;

        try{

            for (Exemplo e : List) {
                classes.add(e.getClasse());
            }
            
            classeFrequente = classes.get(0);
    
            for (String i : classes) {
                curr_frequency = Collections.frequency(classes, i);
    
                if (curr_frequency > counter) {
                    counter = curr_frequency;
                    classeFrequente = i;
                }
            }
           
            for (String i : classes) {
                
                if (Collections.frequency(classes, i) == counter && !classesMaisFrequentes.contains(i)) {
                    classesMaisFrequentes.add(i);
                }
            }
    
            if (classesMaisFrequentes.size() == 1)
                return classeFrequente;
            else
                return classesMaisFrequentes.get(0);

        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Ocorreu um problema ao determinar a classe mais frequente.\n" + e.getMessage());
            throw e;
        }
    }
}
