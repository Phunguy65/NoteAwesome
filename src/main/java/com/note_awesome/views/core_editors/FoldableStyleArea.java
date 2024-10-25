/*
 * Copyright (c) 2013-2023, Tomas Mikula and contributors
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.note_awesome.views.core_editors;

import javafx.scene.Node;
import org.fxmisc.richtext.GenericStyledArea;
import org.fxmisc.richtext.StyledTextArea;
import org.fxmisc.richtext.TextExt;
import org.fxmisc.richtext.model.Paragraph;
import org.fxmisc.richtext.model.SegmentOps;
import org.fxmisc.richtext.model.StyledSegment;
import org.fxmisc.richtext.model.TextOps;
import org.reactfx.util.Either;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class FoldableStyleArea extends GenericStyledArea<ParStyle, Either<String, LinkedImage>, TextStyle> {
    private final static TextOps<String, TextStyle> styledTextOps = SegmentOps.styledTextOps();
    private final static LinkedImageOps<TextStyle> linkedImageOps = new LinkedImageOps<>();

    public FoldableStyleArea() {
        super(
                ParStyle.EMPTY,
                (paragraph, style) -> paragraph.setStyle(style.toCss()),
                TextStyle.EMPTY.updateFontSize(13).updateFontFamily("SF Pro Display"),
                styledTextOps._or(linkedImageOps, (s1, s2) -> Optional.empty()),
                seg -> createNode(seg, (textExt, style) -> textExt.setStyle(style.toCss()))
        );
    }

    private static Node createNode(StyledSegment<Either<String, LinkedImage>, TextStyle> seg,
                                   BiConsumer<? super TextExt, TextStyle> applyStyle) {
        return seg.getSegment().unify(
                text -> StyledTextArea.createStyledTextNode(text, seg.getStyle(), applyStyle),
                LinkedImage::createNode
        );
    }

    public void foldParagraphs(int startPar, int endPar) {
        foldParagraphs(startPar, endPar, getAddFoldStyle());
    }

    public void foldSelectedParagraphs() {
        foldSelectedParagraphs(getAddFoldStyle());
    }

    public void foldText(int start, int end) {
        fold(start, end, getAddFoldStyle());
    }

    public void unfoldParagraphs(int startingFromPar) {
        unfoldParagraphs(startingFromPar, getFoldStyleCheck(), getRemoveFoldStyle());
    }

    public void unfoldText(int startingFromPos) {
        startingFromPos = offsetToPosition(startingFromPos, Bias.Backward).getMajor();
        unfoldParagraphs(startingFromPos, getFoldStyleCheck(), getRemoveFoldStyle());
    }

    protected UnaryOperator<ParStyle> getAddFoldStyle() {
        return style -> style.updateFold(true);
    }

    protected UnaryOperator<ParStyle> getRemoveFoldStyle() {
        return style -> style.updateFold(false);
    }

    protected Predicate<ParStyle> getFoldStyleCheck() {
        return ParStyle::isFolded;
    }
}
