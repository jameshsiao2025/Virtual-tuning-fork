
/** James Hsiao 5/23
 *
 * sound class the extends the simplesound class provided by in the bookclass folder.
 * this class contains the changeVol and changefreq methods that allows me to alter the volume and frequency of the default 
 * sound file I included.
 */
public class Sound extends SimpleSound {

    //constructor with an argument of the type string. 
    public Sound(String fileName) {
        // let the parent class handle setting the file name
        super(fileName);
    }

//method i wrote to change the pitch of the original tone by a factor a floating point decimal. 
    public void changeFreq(double factor) {
        Sound s = new Sound(this.getFileName());
        for (double sourceIndex = 0, targetIndex = 0;
                targetIndex < this.getLength();
                sourceIndex = sourceIndex + factor, targetIndex++) {
            if (sourceIndex >= s.getLength()) {
                sourceIndex = 0;
            }
            this.setSampleValueAt((int) targetIndex, s.getSampleValueAt((int) sourceIndex));
        }
    }

//method i wrote to change the volume of the original ton by a factor of the type int that comes from the volume slider. 
    public void changeVol(int volume) {
        SoundSample[] sArray = this.getSamples();
        SoundSample sample = null;
        double value = 0;
        for (int i = 0; i < sArray.length; i++) {
            sample = sArray[i];
            value = sample.getValue();
            //the final value is divided by 50 to make sure its not too loud on other computers
            sample.setValue((int) (value * volume) / 50);

        }

    }

}
