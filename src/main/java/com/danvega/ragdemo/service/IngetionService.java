package com.danvega.ragdemo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.ParagraphPdfDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;


@Component
public class IngetionService implements CommandLineRunner{
	
	private static final Logger log= LoggerFactory.getLogger(IngetionService.class);
	
	private VectorStore vectorStore;
	
	@Value("classpath:/docs/FINANCIAL-SERVICES-6th-Sem.pdf")
	private Resource leavePolicy;
	

	public IngetionService(VectorStore vectorStore) {
		this.vectorStore = vectorStore;
	}



	@Override
	public void run(String... args) throws Exception {
		var pdfReader = new PagePdfDocumentReader(leavePolicy);
		TextSplitter textSplitter = new TokenTextSplitter();
		vectorStore.accept(textSplitter.apply(pdfReader.get()));
		log.info("vector store loaded with data!");
	}

}
