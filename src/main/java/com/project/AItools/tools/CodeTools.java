package com.project.AItools.tools;

import java.util.function.Function;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import com.project.AItools.record.GenerateJavaCodeRequest;

@Component
public class CodeTools 
{
	private final ChatClient chatClient;
	
	public CodeTools(ChatClient.Builder chatClient)
	{
		this.chatClient = chatClient.build();
	}
	
	@Tool
	Function<GenerateJavaCodeRequest, String> formatCode()
	{
		return request -> {
			
			System.out.println("ferramenta sendo chamada");
			
			String rawCode = request.code();
			
			String systemPrompt = "Você é um formatador de código Java experiente. Sua única tarefa é formatar e indentar corretamente o código fornecido. A resposta deve conter SOMENTE o código formatado.";
			
			String userPrompt = rawCode;
			
			String formattedCode = chatClient
					.prompt()
					.system(systemPrompt)
					.user(userPrompt)
					.call()
					.content();
			
			return formattedCode;
		};
	}
	
	@Tool
	Function<GenerateJavaCodeRequest, String> generateCode()
	{
		return request -> {
			
			System.out.println("ferramenta sendo chamada");
			
			String rawCode = request.code();
			
			String systemPrompt = """
			        Voce é um engenheiro de software senior, formado em ciencias da computação em harvard, sua especialidade 
			        é a linguagem java e sabe de tudo sobre o framework spring. Sua unica tarefa é gerar o codigo pedido pelo usuario.
			        
			        1. **NÃO** adicione explicações, comentários ou texto introdutório antes ou depois do código.
			        2. O código gerado deve ser completo, pronto para compilar e seguir a arquitetura limpa (Clean Architecture).
			        3. Otimize o uso da memória e a performance de threads.
			        4. O código final deve ser envolto em blocos Markdown (```java ... ```) para facilitar a cópia.
			        5. Lembre-se de sempre verificar a atual mais atual do framework spring e da linguagem Java.
			        """;
			
			String userPrompt = rawCode;
			
			String generateCode = chatClient
					.prompt()
					.system(systemPrompt)
					.user(userPrompt)
					.call()
					.content();
			
			return generateCode;
		};
	}
	
}
