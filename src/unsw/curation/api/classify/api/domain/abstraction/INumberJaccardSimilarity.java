package unsw.curation.api.classify.api.domain.abstraction;

import unsw.curation.api.classify.api.domain.ExtractNumberSimilarity;

import java.io.IOException;
import java.util.List;


public interface INumberJaccardSimilarity {

	double Jaccard_Vector_Vector(double[] number1, double[] number2);
	List<ExtractNumberSimilarity> Jaccard_Vector_VectorS(String filePath) throws IOException;
	List<ExtractNumberSimilarity> Jaccard_Vector_VectorS(Double[] vector, String filePath) throws IOException;
}
