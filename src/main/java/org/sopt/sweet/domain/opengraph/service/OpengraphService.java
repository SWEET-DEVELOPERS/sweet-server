package org.sopt.sweet.domain.opengraph.service;

import lombok.RequiredArgsConstructor;
import org.sopt.sweet.domain.opengraph.dto.OpengraphRequestDto;
import org.sopt.sweet.domain.opengraph.dto.OpengraphResponseDto;
import org.sopt.sweet.global.error.exception.EntityNotFoundException;
import org.sopt.sweet.global.external.opengraph.lib.OpenGraph;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.sopt.sweet.global.error.ErrorCode.OPEN_GRAPH_NOT_FOUND;

@RequiredArgsConstructor
@Service
@Transactional
public class OpengraphService {

    public OpengraphResponseDto getData(OpengraphRequestDto opengraphRequestDto) {
        try {
            OpenGraph page = new OpenGraph(opengraphRequestDto.BaseURL(), true);
            return OpengraphResponseDto.of(getContent(page, "title"), getContent(page, "image"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new EntityNotFoundException(OPEN_GRAPH_NOT_FOUND);
        }
    }

    private String getContent(OpenGraph page, String propertyName) {
        try {
            return page.getContent(propertyName);
        } catch (NullPointerException e) {
            throw new EntityNotFoundException(OPEN_GRAPH_NOT_FOUND);
        }
    }
}
