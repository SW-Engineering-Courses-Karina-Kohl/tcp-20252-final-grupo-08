package Sounds;

import utils.EnummerateSounds;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class MusicPlayer {

    private Clip clip;
    private FloatControl volumeControl;

    /**
     * Carrega o arquivo de música
     */
    public MusicPlayer(String filePath) {
        try {
            File audioFile = new File(filePath);

            if (!audioFile.exists()) {
                System.err.println("ERRO: Arquivo de áudio não encontrado -> " + filePath);
                return;
            }

            AudioInputStream audioIn = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audioIn);

            // Controle de volume
            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            } else {
                System.err.println("Este sistema NÃO suporta controle de volume.");
            }

        } catch (UnsupportedAudioFileException e) {
            System.err.println("Formato de áudio não suportado!");
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            System.err.println("Linha de áudio indisponível!");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Erro ao carregar arquivo de áudio!");
            e.printStackTrace();
        }
    }

    /**
     * Toca a música uma vez
     */
    public void play() {
        if (clip == null) return;

        clip.stop();
        clip.setFramePosition(0);
        clip.start();
    }

    /**
     * Toca a música em loop infinito
     */
    public void playLoop() {
        if (clip == null) return;

        clip.stop();
        clip.setFramePosition(0);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Para a música
     */
    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    /**
     * Ajusta o volume entre 0.0 (mudo) e 1.0 (máximo)
     */
    public void setVolume(double volume) {
        if (volumeControl == null)
            return;

        if (volume < 0.0) volume = 0.0;
        if (volume > 1.0) volume = 1.0;

        float min = volumeControl.getMinimum(); // geralmente -80.0f
        float max = volumeControl.getMaximum(); // geralmente 6.0f

        // Mapeamento linear 0.0 -> min, 1.0 -> max
        float gain = (float) (min + (max - min) * volume);
        volumeControl.setValue(gain);
    }


    // =====================================================================
    // ================================ MAIN ================================
    // =====================================================================

    public static void main(String[] args) {
        EnummerateSounds actualSound;


        // Altere o caminho para seu arquivo WAV
        MusicPlayer player = new MusicPlayer("src/Sounds/ambient-background-2-421085.wav");

        System.out.println("Iniciando teste...");

        EnummerateSounds[] sounds = EnummerateSounds.values();

        try {
            Thread.sleep(3000); // espera 3 segundos
        } catch (InterruptedException ignored) {
        }

        // Loop do último índice até 0
        for (int i = sounds.length - 1; i >= 0; i--) {
            System.out.println("Volume -> " + sounds[i].getLabel());
            player.setVolume(sounds[i].getValue());
            System.out.println("Tocando música...");
            player.playLoop();

            try {
                Thread.sleep(3000); // espera 3 segundos
            } catch (InterruptedException ignored) {
            }
        }

        // Parar
        System.out.println("Parando música.");
        player.stop();

        System.out.println("Fim do teste.");
    }
}
