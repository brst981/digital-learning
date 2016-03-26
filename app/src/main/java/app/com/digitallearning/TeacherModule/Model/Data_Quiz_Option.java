package app.com.digitallearning.TeacherModule.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ${PSR} on 3/25/16.
 */
public class Data_Quiz_Option implements Serializable {

    ArrayList<Data_Option> option;
    public ArrayList<Data_Option> getOption() {
        return option;
    }

    public void setOption(ArrayList<Data_Option> option) {
        this.option = option;
    }


}
