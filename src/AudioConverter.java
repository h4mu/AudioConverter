/** A command line application that reads an audio file,
 * converts it to 22.05kHz 16 bit mono signed little endian PCM wave,
 * and saves the converted audio to an output file. */
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

/**
 * @author Tamas Hamor 
 */
public class AudioConverter {

	/**
	 * @param args
	 *            the filenames
	 */
	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("usage: java AudioConverter in.file out.file");
		}
		AudioInputStream stream = null;
		try {
			stream = AudioSystem.getAudioInputStream(new File(args[0]));
		} catch (Exception e) {
			System.out.println("cannot open input file");
			e.printStackTrace();
			return;
		}
		AudioFormat outFormat = new AudioFormat(22050.0F, 16, 1, true, false);
		if (!AudioSystem.isConversionSupported(stream.getFormat(), outFormat)) {
			System.out.println("Conversion not supported");
			return;
		}
		stream = AudioSystem.getAudioInputStream(outFormat,	stream);
		try {
			AudioSystem.write(stream, AudioFileFormat.Type.WAVE, new File(args[1]));
		} catch (IOException e) {
			System.out.println("cannot write to output file");
			e.printStackTrace();
			return;
		}
	}
}
