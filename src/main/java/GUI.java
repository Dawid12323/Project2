import models.Result;
import models.Track;
import rest.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GUI extends JFrame implements ActionListener {
    private JPanel panel;
    private JButton searchButton;
    private JTextField bandName;
    private JList<String> list;
    public List<Track> tracks = new ArrayList<>();
    private DefaultListModel<String> listModel = new DefaultListModel<>();


    /**
     * teraz mam dla Ciebie nowe zadanie
     * po pierwsze przeczytaj uwagi ktore dodalem i w razie czego pytaj
     *
     * wspominałeś że w liscie jest tylko nazwa utworu bez autora, no i tak powinno byc poniewaz
     *
     *  private DefaultListModel<String> listModel = new DefaultListModel<>(); przyjmuje obiekty typu String
     *  natomiast w naszej pętli dodajemy  listModel.addElement(track.getTrackName());
     *  czyli do naszej listy trafiają kolejne rekordy, gdzie kazdy z nich to kolejny track.getTrackName() z listy jąką dostalismy w requescie
     *  track.getTrackName() -> nazwa utworu, tylko bez autora
     *
     *
     *  Nowe zadanie polega na tym ze klikam w dany item na liscie np. In the End i otwiera mi sie nowe okienko w ktorym mam takie dane
     *
     *  autor, nazwa utworu, data premiery
     *
     *
     * można to zrobic na dwa sposoby my uzyjemy tego prostego bo ten drugi jest za trudny
     *
     * Mamy liste public List<Track> tracks = new ArrayList<>(); ktora obecnie jest nie uzuwana, dodajmy do niej elementy z listy List<Track> results;
     * ktora bedziemy miec w response.body()
     *
     * gdzieki temu jak klikniemy w dany utwor na naszej JLiscie to w z niej bedziemy mogli wyciagnac tylko nazwe utworu,
     * ale tez jego pozycje(np. ma index 3 bo jest na 4 pozycji listy), i wtedy skorzystamy z listy  public List<Track> tracks; bo e niej pod tym
     * samym indeksie bedzie juz nie tylko String(nazwa utworu) ale cały obiekt Track z którego mozna wyciagnąć inne pola(bedziesz je musial dodac do obiektu Track)
     *
     * Pisz smiało bo jest to ambitne zadanie i na pewno bedziesz mial mnostwo pytan i trudnosci ;)
     *
     */


    public GUI() {
        initFrame();
        searchButton.addActionListener(this);

        /*
         Czytamy komunikaty, tutaj nam mowi ze nadpiszemy sobie liste wygenerowana przez UI designer
          efekt jest taki, że nic nam sie nie wyswietli
          mozna to naprawic poprzez:

           - ustawienie rozmiaru listy(obecnie ma 0 szerokosc i 0 wysokosc
           - dodanie listy do panelu

               list = new JList<>(listModel);
                  list.setSize(new Dimension(400, 400));
                  panel.add(list);

          Kod powyżej rozwiąże problem jednak lepszym rozwiazaniem jest usunać linijkę
          list = new JList<>(listModel);
          ponieważ lista jest już utworzona poprzez UI designer-a, co prawda nie ma ona w sobie naszej listy
          listModel jednak dodamy ją poźniej poprzez
          list.setModel(listModel);

         */

    }

    /**
     * Metoda initializuje nam naszą ramkę
     * możemy to zrobić tutaj i w taki sposób ponieważ ta klasa dziedziczy po JFrame
     * ustawiamy kolejno content panel
     * rozmiar
     * i ustawiamy widocznosc
     */
    private void initFrame() {
        this.setContentPane(panel);
        this.setSize(500, 500);
        this.setVisible(true);
    }

    /**
     *
     * @param event - event uruchomiony poprzez wykonanie interakcji z componentem
     */
    @Override
    public void actionPerformed(ActionEvent event) {

        //sprawdzenie czy component z którym weszliśmy w interakcje to searchButton
        if (event.getSource() == searchButton) {

            //wykonanie requestu w sposób asynchroniczny
            RestClient.getClient().getAllTracks(bandName.getText()).enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    if (response.isSuccessful()) {
                        System.out.println("response is successful");

                         /*
                        czytamy komunikaty getResults nam się swięci i mowi nam ze program moze nam sie tutaj wywalic przez NullPointerException
                        a to dlatego, ze pomino iz response.body() - czyli de facto obiekt Result - nie jest nullem to nie dokonca mozemy miec pewnosc czy respons.body()
                        jest poprawnie zaincjalizowanym obiektem Result.

                        Zmiana kodu na poniższy rozwiązuje problem

                        if (result != null) {
                            result.getResults().forEach(track -> {
                                listModel.addElement(track.getTrackName());
                            });
                            list.setModel(listModel);
                        }
                         */


                        if (response.body() != null) {
                            response.body().getResults().forEach(track -> {
                                listModel.addElement(track.getTrackName());
                            });
                            list.setModel(listModel);
                        }
                    } else {
                        System.out.println("blad serwera");
                    }
                }

                @Override
                public void onFailure(Call<Result> call, Throwable throwable) {
                    System.out.println("blad w stylu timeout/blad serializacji itp, czyli blad u nas");
                }
            });
        }
    }
}