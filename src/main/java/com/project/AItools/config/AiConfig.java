package com.project.AItools.config;

import java.util.List;
import java.util.function.Function;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig
{
	@Bean
    ChatClient configuredChatClient(
        ChatClient.Builder chatClientBuilder, 
        ChatMemory chatMemory,
        List<Function<?, ?>> toolFunctions 
    ) 
	{
		String primarySystemPrompt = """
		        Você é um Assistente de Codificação Senior. Seu objetivo primário é ajudar o usuário
		        executando as ferramentas disponíveis para você.
		        
		        Sempre que o usuário pedir para CRIAR código, use a ferramenta 'generateCode'.
		        Sempre que o usuário pedir para FORMATAR código, use a ferramenta 'formatCode'.
		        NUNCA gere código ou formate-o diretamente na resposta; DELEGUE a tarefa à ferramenta apropriada.
		        """;
		
        Object[] toolObjects = toolFunctions.toArray();

        ToolCallback[] toolCallbacks = ToolCallbacks.from(toolObjects); 

        return chatClientBuilder
            .defaultAdvisors(
                MessageChatMemoryAdvisor.builder(chatMemory).build()
            )
            .defaultSystem(primarySystemPrompt)
            .defaultToolCallbacks(toolCallbacks) 
            .build();
    }
}
