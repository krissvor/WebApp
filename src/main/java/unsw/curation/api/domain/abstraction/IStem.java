package unsw.curation.api.domain.abstraction;

import unsw.curation.api.domain.ExtractStem;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface IStem {

	void ReadDataset() throws FileNotFoundException, IOException, URISyntaxException;
	List<ExtractStem> FindWordDerivedForms(String word) throws FileNotFoundException, IOException, URISyntaxException;
}
