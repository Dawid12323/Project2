import models.Result;
import models.Track;
import rest.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.swing.*;
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

    public GUI() {
        initFrame();
        searchButton.addActionListener(this);
        list = new JList<>(listModel);
    }

    private void initFrame() {
        this.setContentPane(panel);
        this.setSize(500, 500);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == searchButton) {
            RestClient.getClient().getAllTracks(bandName.getText()).enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    if (response.isSuccessful()) {
                        System.out.println("lol");
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