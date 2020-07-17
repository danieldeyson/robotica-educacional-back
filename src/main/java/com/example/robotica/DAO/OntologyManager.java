package com.example.robotica.DAO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;

import java.util.LinkedList;
import java.util.List;



import com.example.robotica.Model.Aula;
import com.example.robotica.Model.Programacao;
import com.example.robotica.Model.Robo;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.ontology.Ontology;
import org.apache.jena.query.QueryExecException;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.reasoner.ValidityReport;
import org.apache.jena.shared.JenaException;
import org.apache.jena.update.UpdateAction;

public class OntologyManager {

	private String URI;					//URI da ontologia
	private OntModel assertedModel;		//Modelo definido que guarda a estrutura da ontologia
	private InfModel inferredModel;		//Modelo inferido da ontologia
	private final String fileName; // Pasta onde se encontra a ontologia

	public OntologyManager() {
		this.fileName = "ontologia.owl";
		loadOntology();
	}

	public OntologyManager(final String fileName) {
		this.fileName = fileName;
		loadOntology();
	}

	private void loadOntology() {

		assertedModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);

		System.out.println("Loading Ontology " + fileName + " ...");
		try {
			final InputStream input = org.apache.jena.util.FileManager.get().open(fileName);

			try {
				assertedModel.read(input, null);
				System.out.println("Load Complete...");
			} catch (final Exception e) {
				e.printStackTrace();
			}

			/*
			 * assertedModel pode armazenar mais de uma ontologia por isso deve-se buscar as
			 * ontologias inseridas nele e obter sua URI
			 */
			final Iterator<?> iter = assertedModel.listOntologies();

			// Obtendo a URI da ontologia
			if (iter.hasNext()) {
				final Ontology ontology = (Ontology) iter.next();
				this.URI = ontology.getURI();
			}

		} catch (final JenaException je) {
			System.err.println("ERROR" + je.getMessage());
			je.printStackTrace();
			System.exit(0);
		}
	}

	// Executa reasoner padrão na ontologia
	public boolean executeReasoner() {
		boolean success = false;
		System.out.println("Executing Reasoner...");
		try {
			/*
			 * Existem vários tipos de reasoner, os OWL, os RDF e variações deles
			 */
			final Reasoner reasoner = ReasonerRegistry.getRDFSReasoner();
			inferredModel = ModelFactory.createInfModel(reasoner, assertedModel);
			validateModel(inferredModel);
			success = true;
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return success;
	}

	/* Imprime toda a estrutura da ontologia */
	public void printAssertedOntModel() {
		System.out.println("Printing Model...");
		assertedModel.write(System.out, "TTL");
		// assertedModel.write(System.out, "TTL");
	}

	/*
	 * Método para salvar ontologia no format passada no modelo e no local RDF/XML
	 */
	public boolean saveOntology(final String filePath, final OntModel model) {
		boolean executeSave = false;
		final StringWriter sw = new StringWriter();
		model.write(sw, "RDF/XML");
		final String owlCode = sw.toString();
		final File file = new File(filePath);
		try {
			final FileWriter fw = new FileWriter(file);
			fw.write(owlCode);
			fw.close();
			executeSave = true;
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return executeSave;
	}

	/* Método para salvar ontologia no format RDF/XML no próprio arquivo */
	public void saveOntology() {
		final StringWriter sw = new StringWriter();
		assertedModel.write(sw, "RDF/XML");
		final String owlCode = sw.toString();
		final File file = new File(fileName);
		try {
			final FileWriter fw = new FileWriter(file);
			fw.write(owlCode);
			fw.close();
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	// Método que valida o modelo inferido (Não é 100% eficaz)
	public boolean validateModel(final InfModel inferredModel) {
		boolean consistent = false;
		System.out.print("...Validating Inferred Model: ");
		try {
			final ValidityReport validity = inferredModel.validate();
			if (validity.isValid()) {
				System.out.println("Consistent...");
				consistent = true;
			} else {
				System.out.println("Conflicts...");
				for (final Iterator<?> i = validity.getReports(); i.hasNext();) {
					System.out.println(" - " + i.next());
				}
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return consistent;
	}

	void insertAttributePerformance(final String individualPerfomance, final String attribute,
			final float performanceValue, final String propertyFirst, final String propertyMeans) {
		System.out.println("Executing SPARQL Insert:");
		final String owl = "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n";
		final String rdf = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n";
		final String rdfs = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n";
		final String xsd = "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n";
		final String ont = "PREFIX ont: <" + URI + "#>\n";
		String insertSparql = owl + rdf + rdfs + xsd + ont;
		insertSparql += "INSERT DATA {\n" + "\tont:" + individualPerfomance + " rdf:type ont:Desempenho.\n" + "\tont:"
				+ individualPerfomance + " ont:" + propertyFirst + " \"" + performanceValue + "\"^^xsd:float.\n"
				+ "\tont:" + individualPerfomance + " ont:" + propertyMeans + " \"" + performanceValue
				+ "\"^^xsd:float.\n" + "\tont:" + individualPerfomance + " ont:eDesempenho" + " ont:" + attribute + "\n"
				+ "}";

		System.out.println(insertSparql);

		try {
			UpdateAction.parseExecute(insertSparql, assertedModel);
		} catch (final Exception e) {
			System.out.println("Falha ao executar UPDATE...");
			System.out.println(e.getMessage());
		}

	}

	public ArrayList<Aula> query() {
		final ArrayList<Aula> result = new ArrayList<Aula>();
	
		final String owl = "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n";
		final String rdf = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n";
		final String rdfs = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n";
		final String xsd = "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n";
		final String ont = "PREFIX ont: <" + URI + "#>\n";
		String sparqlQuery = owl + rdf + rdfs + xsd + ont;

		sparqlQuery += "SELECT DISTINCT ?Disciplina ?Assuntos ?Descricao ?DescricaoRobo ?conteudo  ?AtividadeEspecifica ?Serie ?Robos ?Programacao  ?RecursoDidadico ?Referencia ?Duracao ?Objetivo ?Aula \r\n"
				+ "WHERE {\r\n" + "#Aula\r\n" + "?Aula rdfs:subClassOf ?subclasseaula.\r\n"
				+ " ?subclasseaula owl:someValuesFrom ?conteudo.\r\n" +

				"#Conteudo\r\n" + "?conteudo rdfs:subClassOf* ont:ComponentesCurriculares.\r\n" +

				"#Assunto \r\n" + "?conteudo rdfs:subClassOf ?Assuntos.\r\n"
				+ "?Assuntos rdfs:subClassOf*  ?Disciplina.\r\n" +

				"#Disciplina\r\n" + "?Disciplina rdfs:subClassOf ont:ComponentesCurriculares. \r\n" +

				"#AtividadeEspecifica\r\n" + "?conteudo rdfs:subClassOf ?atividade.\r\n"
				+ "?atividade owl:someValuesFrom ?AtividadeEspecifica.\r\n"
				+ "?AtividadeEspecifica rdfs:comment ?Descricao.\r\n" +

				"#Recurso Didadico\r\n" + "?Aula rdfs:subClassOf ?x.\r\n" + "?x owl:hasValue?RecursoDidadico.\r\n"
				+ "?x owl:onProperty ont:TemRecursoDidatico.\r\n" +

				"#Referencia\r\n" + "?Aula rdfs:subClassOf ?y.\r\n" + "?y owl:hasValue?Referencia.\r\n"
				+ "?y owl:onProperty ont:TemReferencia.  \r\n" +

				"#Duracao \r\n" + "?Aula rdfs:subClassOf ?z.\r\n" + "?z owl:hasValue?Duracao.\r\n"
				+ "?z owl:onProperty ont:TemDuracao.\r\n" +

				"#Objetivo\r\n" + "?Aula rdfs:subClassOf ?w.\r\n" + "?w owl:hasValue?Objetivo.\r\n"
				+ "?w owl:onProperty ont:TemObjetivo.\r\n" +

				"#Serie\r\n" + "?conteudo rdfs:subClassOf ?a.\r\n" + "?a owl:hasValue ?Serie.\r\n"
				+ "?a owl:onProperty ont:TemSerie.\r\n" + "#Robo e Programacao\r\n"
				+ "?AtividadeEspecifica rdfs:subClassOf ?robo.\r\n" + "?Robos rdfs:comment ?DescricaoRobo.\r\n"
				+ "?robo owl:someValuesFrom ?Programacao.\r\n" + "?programacao owl:someValuesFrom ?Robos.\r\n"
				+ "?Robos rdfs:subClassOf ont:Robos.\r\n"
				+ "FILTER NOT EXISTS {?Programacao rdfs:subClassOf* ont:Robos}\r\n" +

				"\r\n" + "\r\n" + "}";
		
		try {
			final QueryExecution qexec = QueryExecutionFactory.create(sparqlQuery, assertedModel);
			final ResultSet results = qexec.execSelect();
			String nome = "";
			while (results.hasNext()) {
				final Aula aula = new Aula();
				final QuerySolution soln = results.nextSolution();

				aula.setNome(soln.getResource("?Aula").getLocalName().toString());
				aula.setConteudo(soln.getResource("?conteudo").getLocalName().toString());
				aula.setAssunto(soln.getResource("?Assuntos").getLocalName().toString());
				aula.setDisciplina(soln.getResource("?Disciplina").getLocalName().toString());
				aula.setAtividade(soln.getResource("?AtividadeEspecifica").getLocalName().toString());
				aula.setRobo(soln.getResource("?Robos").getLocalName().toString());
				aula.setProgramacao(soln.getResource("?Programacao").toString());
				aula.setRecursoDidatico(soln.getLiteral("?RecursoDidadico").getString());
				aula.setDuracao(soln.getLiteral("?Duracao").getString());
				aula.setSerie(soln.getLiteral("?Serie").getString());
				aula.setReferencia(soln.getLiteral("?Referencia").getString());
				aula.setObjetivo(soln.getLiteral("?Objetivo").getString());
				aula.setDescricao(soln.getLiteral("?Descricao").getString());
				aula.setDescricaoRobo(soln.getLiteral("?DescricaoRobo").getString());

				if (!nome.equals(aula.getAtividade())) {
					nome = aula.getAtividade();
					result.add(aula);
				}
			}
			qexec.close();
		} catch (final QueryExecException e) {
			System.err.println("ERROR" + e.getMessage());
			e.printStackTrace();
			// System.exit(0);
		}
		return result;
	}

	public ArrayList<Robo> selectRobos() {
		final ArrayList<Robo> result = new ArrayList<Robo>();
		
		final String owl = "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n";
		final String rdf = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n";
		final String rdfs = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n";
		final String xsd = "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n";
		final String ont = "PREFIX ont: <http://www.semanticweb.org/les-10/ontologies/2019/8/untitled-ontology-12#>\n";
		String sparqlQuery = owl + rdf + rdfs + xsd + ont;
		sparqlQuery += "SELECT DISTINCT ?pecas  ?DescricaoRobo ?Descricao ?quantidade\r\n" + "WHERE {\r\n"
				+ "ont:RoboEducador owl:equivalentClass/owl:intersectionOf ?list .\r\n"
				+ "?Robos rdfs:comment ?DescricaoRobo.\r\n" + "?list rdf:rest*/rdf:first ?element .\r\n"
				+ "?element owl:someValuesFrom ?montagem .\r\n" + "?montagem owl:intersectionOf ?pecasLista1 .\r\n"
				+ "?montagem owl:intersectionOf ?pecasLista2 .\r\n" + "?pecasLista1 rdf:rest*/rdf:first ?pecas .\r\n"
				+ "?pecasLista2 rdf:rest*/rdf:first ?peca2 .\r\n" + "?peca2 owl:hasValue ?quantidade.\r\n"
				+ "?pecas rdfs:comment ?Descricao\r\n" + "FILTER NOT EXISTS {?pecas owl:onProperty ont:TemQuantidade}}";

		try {
			final Robo robot = new Robo();
			final QueryExecution qexec = QueryExecutionFactory.create(sparqlQuery, assertedModel);
			final ResultSet results = qexec.execSelect();
			final QuerySolution sol = results.nextSolution();
			results.hasNext();
			robot.setPecas(sol.getResource("?pecas").getLocalName().toString());
			robot.setDescricao(sol.getLiteral("?Descricao").getString());
			robot.setQuantidade(sol.getLiteral("?quantidade").getString());
			robot.setNome(sol.getLiteral("?DescricaoRobo").getString());

			result.add(robot);
			while (results.hasNext()) {

				final Robo robo = new Robo();
				final QuerySolution soln = results.nextSolution();

				robo.setPecas(soln.getResource("?pecas").getLocalName().toString());
				robo.setDescricao(soln.getLiteral("?Descricao").getString());
				robo.setQuantidade(soln.getLiteral("?quantidade").getString());
				robo.setNome(soln.getLiteral("?DescricaoRobo").getString());

				if (result.get(0).getPecas().contains(robo.getPecas())) {
					qexec.close();

					return result;

				}
				result.add(robo);

			}
			qexec.close();

			return result;
		} catch (final QueryExecException e) {
			System.err.println("ERROR" + e.getMessage());
			e.printStackTrace();
			// System.exit(0);
		}
		return result;
	}
	
	public ArrayList<Programacao> selectProgramacao() {
		final ArrayList<Programacao> result = new ArrayList<Programacao>();

		final String owl = "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n";
		final String rdf = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n";
		final String rdfs = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n";
		final String xsd = "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n";
		final String ont = "PREFIX ont: <http://www.semanticweb.org/les-10/ontologies/2019/8/untitled-ontology-12#>\n";
		String sparqlQuery = owl + rdf + rdfs + xsd + ont;
		sparqlQuery += " SELECT  ?quantidadedepeca  ?atividade ?blocodeprogramacao ?descricao\r\n"
				+ " WHERE { \r\n" + "{\r\n" + "?atividade rdfs:subClassOf ont:Atividade.\r\n"
				+ "?atividade rdfs:subClassOf ?x.\r\n" + "?x owl:someValuesFrom ?a.\r\n"
				+ "?a owl:intersectionOf ?b.\r\n" + "?b rdf:first ?c.\r\n" + "?c rdfs:subClassOf ?d.\r\n"
				+ "?d owl:someValuesFrom ?blocodeprogramacao.\r\n" + "?blocodeprogramacao rdfs:comment ?descricao.\r\n"
				+ "?a owl:intersectionOf ?f.\r\n" + "?f rdf:rest*/rdf:first ?g.\r\n"
				+ "?g owl:hasValue ?quantidadedepeca\r\n" + "}\r\n" + "UNION\r\n" + "{\r\n"
				+ "?atividade rdfs:subClassOf ont:Atividade.\r\n" + "?atividade rdfs:subClassOf ?x.\r\n"
				+ "?x owl:someValuesFrom ?a.\r\n" + "?a rdfs:subClassOf ?b.\r\n"
				+ "?b owl:someValuesFrom ?blocodeprogramacao.\r\n" + "?blocodeprogramacao rdfs:comment ?descricao\r\n"

				+ "}\r\n" + "}";  

		try {
			final QueryExecution qexec = QueryExecutionFactory.create(sparqlQuery, assertedModel);
			final ResultSet results = qexec.execSelect();
			while (results.hasNext()) {
				final Programacao prog = new Programacao();
				final QuerySolution soln = results.nextSolution();

				prog.setBloco(soln.getResource("?blocodeprogramacao").getLocalName().toString());
				prog.setDescript(soln.getLiteral("?descricao").getString());
				prog.setQuantidadePecas(soln.getLiteral("?quantidadedepeca").getString());
				prog.setAtividade(soln.getResource("?atividade").getLocalName().toString());

				result.add(prog);

			}
			qexec.close();
		} catch (final QueryExecException e) {
			System.err.println("ERROR" + e.getMessage());
			e.printStackTrace();
			// System.exit(0);
		}
		return result;
	}

	public ArrayList<Programacao> selectProgramacaoCompleta() {
		final ArrayList<Programacao> result = new ArrayList<Programacao>();

		final String owl = "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n";
		final String rdf = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n";
		final String rdfs = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n";
		final String xsd = "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n";
		final String ont = "PREFIX ont: <http://www.semanticweb.org/les-10/ontologies/2019/8/untitled-ontology-12#>\n";
		String sparqlQuery = owl + rdf + rdfs + xsd + ont;
		sparqlQuery += " SELECT DISTINCT  ?atividade  ?tarefa ?descricaodatarefa ?blocodeprogramacao ?descricao ?quantidadedepeca  \r\n" +
		"WHERE { \r\n" + 
			"{?atividade rdfs:subClassOf ont:Atividade. \r\n" +
				"?atividade rdfs:subClassOf ?x. \r\n" +
				"?x owl:someValuesFrom ?a. \r\n" +
				"?a owl:intersectionOf ?b. \r\n" +
				"?b rdf:first ?tarefa. \r\n" +
				"?tarefa rdfs:subClassOf ?d. \r\n" +
				"?tarefa rdfs:comment ?descricaodatarefa. \r\n" +
				"?d owl:someValuesFrom ?blocodeprogramacao. \r\n" +
				"?blocodeprogramacao rdfs:comment ?descricao. \r\n" +
				"?a owl:intersectionOf ?f. \r\n" +
				"?f rdf:rest*/rdf:first ?g. \r\n" +
				"?g owl:hasValue ?quantidadedepeca \r\n" +
			"}  \r\n" +
			"UNION \r\n" +
			"{ \r\n" +
				"?atividade rdfs:subClassOf ont:Atividade. \r\n" +
				"?atividade rdfs:subClassOf ?x. \r\n" +
				"?x owl:someValuesFrom ?a. \r\n" +
				"?a rdfs:subClassOf ?b. \r\n" +
				"?b owl:someValuesFrom ?blocodeprogramacao. \r\n" +
				"?blocodeprogramacao rdfs:comment ?descricao \r\n" +
				
			"} \r\n" +
		"}";  

		try {
			final QueryExecution qexec = QueryExecutionFactory.create(sparqlQuery, assertedModel);
			final ResultSet results = qexec.execSelect();
			while (results.hasNext()) {
				final Programacao prog = new Programacao();
				final QuerySolution soln = results.nextSolution();

				prog.setBloco(soln.getResource("?blocodeprogramacao").getLocalName().toString());
				prog.setDescript(soln.getLiteral("?descricao").getString());
				prog.setQuantidadePecas(soln.getLiteral("?quantidadedepeca").getString());
				prog.setAtividade(soln.getResource("?atividade").getLocalName().toString());
				prog.setTarefa(soln.getResource("?tarefa").getLocalName().toString());
				prog.setDescTarefa(soln.getLiteral("?descricaodatarefa").getString());

				result.add(prog);

			}
			qexec.close();
		} catch (final QueryExecException e) {
			System.err.println("ERROR" + e.getMessage());
			e.printStackTrace();
			// System.exit(0);
		}
		return result;
	}

	public ArrayList<Aula> selectConteudo() {
		final ArrayList<Aula> result = new ArrayList<Aula>();
		System.out.println("Executing SPARQL SELECT:");
		final String owl = "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n";
		final String rdf = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n";
		final String rdfs = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n";
		final String xsd = "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n";
		final String ont = "PREFIX ont: <" + URI + "#>\n";
		String sparqlQuery = owl + rdf + rdfs + xsd + ont;

		sparqlQuery += "SELECT DISTINCT  ?Assuntos ?conteudo ?Serie \r\n" + "WHERE {\r\n" + "#Aula\r\n"
				+ "?Aula rdfs:subClassOf ?subclasseaula.\r\n" + " ?subclasseaula owl:someValuesFrom ?conteudo.\r\n" +

				"#Conteudo\r\n" + "?conteudo rdfs:subClassOf* ont:ComponentesCurriculares.\r\n" +

				"#Assunto \r\n" + "?conteudo rdfs:subClassOf ?Assuntos.\r\n"
				+ "?Assuntos rdfs:subClassOf*  ?Disciplina.\r\n" +

				"#Disciplina\r\n" + "?Disciplina rdfs:subClassOf ont:ComponentesCurriculares. \r\n" +

				"#AtividadeEspecifica\r\n" + "?conteudo rdfs:subClassOf ?atividade.\r\n"
				+ "?atividade owl:someValuesFrom ?AtividadeEspecifica.\r\n" +

				"#Recurso Didadico\r\n" + "?Aula rdfs:subClassOf ?x.\r\n" + "?x owl:hasValue?RecursoDidadico.\r\n"
				+ "?x owl:onProperty ont:TemRecursoDidatico.\r\n" +

				"#Referencia\r\n" + "?Aula rdfs:subClassOf ?y.\r\n" + "?y owl:hasValue?Referencia.\r\n"
				+ "?y owl:onProperty ont:TemReferencia.  \r\n" +

				"#Duracao \r\n" + "?Aula rdfs:subClassOf ?z.\r\n" + "?z owl:hasValue?Duracao.\r\n"
				+ "?z owl:onProperty ont:TemDuracao.\r\n" +

				"#Objetivo\r\n" + "?Aula rdfs:subClassOf ?w.\r\n" + "?w owl:hasValue?Objetivo.\r\n"
				+ "?w owl:onProperty ont:TemObjetivo.\r\n" +

				"#Serie\r\n" + "?conteudo rdfs:subClassOf ?a.\r\n" + "?a owl:hasValue ?Serie.\r\n"
				+ "?a owl:onProperty ont:TemSerie.\r\n" + "#Robo e Programacao\r\n"
				+ "?AtividadeEspecifica rdfs:subClassOf ?robo.\r\n" + "?robo owl:someValuesFrom ?Programacao.\r\n"
				+ "?programacao owl:someValuesFrom ?Robos.\r\n" + "?Robos rdfs:subClassOf ont:Robos.\r\n"
				+ "FILTER NOT EXISTS {?Programacao rdfs:subClassOf* ont:Robos}\r\n" +

				"\r\n" + "\r\n" + "}";
		
		try {
			final QueryExecution qexec = QueryExecutionFactory.create(sparqlQuery, assertedModel);
			final ResultSet results = qexec.execSelect();
			while (results.hasNext()) {
				final Aula aula = new Aula();
				final QuerySolution soln = results.nextSolution();

				aula.setConteudo(soln.getResource("?conteudo").getLocalName().toString());
				aula.setAssunto(soln.getResource("?Assuntos").getLocalName().toString());
				aula.setSerie(soln.getLiteral("?Serie").getString());

				result.add(aula);

			}
			qexec.close();
		} catch (final QueryExecException e) {
			System.err.println("ERROR" + e.getMessage());
			e.printStackTrace();
			// System.exit(0);
		}
		return result;
	}




	public ArrayList<Aula> selectConteudoAssunto() {
		final ArrayList<Aula> result = new ArrayList<Aula>();
	
		final String owl = "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n";
		final String rdf = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n";
		final String rdfs = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n";
		final String xsd = "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n";
		final String ont = "PREFIX ont: <" + URI + "#>\n";
		String sparqlQuery = owl + rdf + rdfs + xsd + ont;

		sparqlQuery += "SELECT ?conteudo ?Assunto ?Serie\r\n"+
		"WHERE { \r\n"+ 
			"?conteudo rdfs:subClassOf ?Assunto.\r\n"+
		   "?Assunto rdfs:subClassOf ont:Matemática.\r\n"+
		   "?conteudo rdfs:subClassOf ?x.\r\n"+
		   "?x owl:hasValue ?Serie.\r\n"+
		

		"}";
		try {
			final QueryExecution qexec = QueryExecutionFactory.create(sparqlQuery, assertedModel);
			final ResultSet results = qexec.execSelect();
			while (results.hasNext()) {
				final Aula aula = new Aula();
				final QuerySolution soln = results.nextSolution();

				aula.setConteudo(soln.getResource("?conteudo").getLocalName().toString());
				aula.setAssunto(soln.getResource("?Assunto").getLocalName().toString());
				aula.setSerie(soln.getLiteral("?Serie").getString());
				

				result.add(aula);

			}
			qexec.close();
		} catch (final QueryExecException e) {
			System.err.println("ERROR" + e.getMessage());
			e.printStackTrace();
			// System.exit(0);
		}
		return result;
	}






	public ArrayList<String> selectSeries() {

		final ArrayList<String> result = new ArrayList<String>();

		System.out.println("Executing SPARQL SELECT:");
		final String owl = "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n";
		final String rdf = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n";
		final String rdfs = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n";
		final String xsd = "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n";
		final String ont = "PREFIX ont: <" + URI + "#>\n";
		String sparqlQuery = owl + rdf + rdfs + xsd + ont;

		sparqlQuery += "SELECT DISTINCT ?Serie  \r\n" + "WHERE {\r\n" + "#Aula\r\n"
				+ "?Aula rdfs:subClassOf ?subclasseaula.\r\n" + " ?subclasseaula owl:someValuesFrom ?conteudo.\r\n" +

				"#Conteudo\r\n" + "?conteudo rdfs:subClassOf* ont:ComponentesCurriculares.\r\n" +

				"#Assunto \r\n" + "?conteudo rdfs:subClassOf ?Assuntos.\r\n"
				+ "?Assuntos rdfs:subClassOf*  ?Disciplina.\r\n" +

				"#Disciplina\r\n" + "?Disciplina rdfs:subClassOf ont:ComponentesCurriculares. \r\n" +

				"#AtividadeEspecifica\r\n" + "?conteudo rdfs:subClassOf ?atividade.\r\n"
				+ "?atividade owl:someValuesFrom ?AtividadeEspecifica.\r\n" +

				"#Recurso Didadico\r\n" + "?Aula rdfs:subClassOf ?x.\r\n" + "?x owl:hasValue?RecursoDidadico.\r\n"
				+ "?x owl:onProperty ont:TemRecursoDidatico.\r\n" +

				"#Referencia\r\n" + "?Aula rdfs:subClassOf ?y.\r\n" + "?y owl:hasValue?Referencia.\r\n"
				+ "?y owl:onProperty ont:TemReferencia.  \r\n" +

				"#Duracao \r\n" + "?Aula rdfs:subClassOf ?z.\r\n" + "?z owl:hasValue?Duracao.\r\n"
				+ "?z owl:onProperty ont:TemDuracao.\r\n" +

				"#Objetivo\r\n" + "?Aula rdfs:subClassOf ?w.\r\n" + "?w owl:hasValue?Objetivo.\r\n"
				+ "?w owl:onProperty ont:TemObjetivo.\r\n" +

				"#Serie\r\n" + "?conteudo rdfs:subClassOf ?a.\r\n" + "?a owl:hasValue ?Serie.\r\n"
				+ "?a owl:onProperty ont:TemSerie.\r\n" + "#Robo e Programacao\r\n"
				+ "?AtividadeEspecifica rdfs:subClassOf ?robo.\r\n" + "?robo owl:someValuesFrom ?Programacao.\r\n"
				+ "?programacao owl:someValuesFrom ?Robos.\r\n" + "?Robos rdfs:subClassOf ont:Robos.\r\n"
				+ "FILTER NOT EXISTS {?Programacao rdfs:subClassOf* ont:Robos}\r\n" +

				"\r\n" + "\r\n" + "}";

		try {
			final QueryExecution qexec = QueryExecutionFactory.create(sparqlQuery, assertedModel);
			final ResultSet results = qexec.execSelect();
			while (results.hasNext()) {
				final QuerySolution soln = results.nextSolution();

				result.add(soln.getLiteral("?Serie").getString());

			}
			qexec.close();
		} catch (final QueryExecException e) {
			System.err.println("ERROR" + e.getMessage());
			e.printStackTrace();
			// System.exit(0);
		}
		return result;
	}

	public List<Aula> paginar(List<Aula> aulas, final int id) {
		int i = 0;

		final List<Aula> list = new LinkedList<Aula>();

		for (final Aula a : aulas) {

			if(i<=(id*10) && (i>=((id-1)*10))){
				
				list.add(a);
				
				
			}
			i++;
		}
			
		return  list;
		


}
}