package unsw.curation.api.classify.api.domain.abstraction;

import unsw.curation.api.classify.api.domain.Classification;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface IClassificationTextSVM {

	void LoadDataset(File arffFileName) throws IOException;
	List<Classification> EvaluateSVM() throws Exception;
	void LearnSVM() throws Exception;
	void SaveModel(String modelName) throws FileNotFoundException, IOException;
}
