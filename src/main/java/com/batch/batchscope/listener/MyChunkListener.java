package com.batch.batchscope.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MyChunkListener implements ChunkListener {
    @Override
    public void beforeChunk(ChunkContext chunkContext) {
        log.info("testtin chunk listener");
    }

    @Override
    public void afterChunk(ChunkContext chunkContext) {
        if (!chunkContext.isComplete()) {
            log.warn("chunk incomplete. stepExec:" + chunkContext.getStepContext().getStepExecution());
        }
    }

    @Override
    public void afterChunkError(ChunkContext chunkContext) {
        log.debug("erreur in chunk. ctx " + chunkContext + " stepExec:" + chunkContext.getStepContext().getStepExecution());

    }
}
