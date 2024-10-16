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

import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import org.fxmisc.richtext.model.Codec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import static javafx.scene.text.TextAlignment.*;

public class ParStyle {
    public static final ParStyle EMPTY = new ParStyle();

    public static final Codec<ParStyle> CODEC = new Codec<ParStyle>() {

        private final Codec<Optional<TextAlignment>> OPT_ALIGNMENT_CODEC =
                Codec.optionalCodec(Codec.enumCodec(TextAlignment.class));
        private final Codec<Optional<Color>> OPT_COLOR_CODEC =
                Codec.optionalCodec(Codec.COLOR_CODEC);

        @Override
        public String getName() {
            return "par-style";
        }

        @Override
        public void encode(DataOutputStream os, ParStyle t) throws IOException {
            OPT_ALIGNMENT_CODEC.encode(os, t.alignment);
            OPT_COLOR_CODEC.encode(os, t.backgroundColor);
            os.writeInt( t.indent.map( i -> Integer.valueOf( i.level ) ).orElse( 0 ) );
            os.writeInt( t.foldCount );
        }

        @Override
        public ParStyle decode(DataInputStream is) throws IOException {
            return new ParStyle(
                    OPT_ALIGNMENT_CODEC.decode(is),
                    OPT_COLOR_CODEC.decode(is),
                    Optional.of( new Indent( is.readInt() ) ),
                    is.readInt() );
        }

    };

    public static ParStyle alignLeft() { return EMPTY.updateAlignment(LEFT); }
    public static ParStyle alignCenter() { return EMPTY.updateAlignment(CENTER); }
    public static ParStyle alignRight() { return EMPTY.updateAlignment(RIGHT); }
    public static ParStyle alignJustify() { return EMPTY.updateAlignment(JUSTIFY); }
    public static ParStyle backgroundColor(Color color) { return EMPTY.updateBackgroundColor(color); }
    public static ParStyle folded() { return EMPTY.updateFold(Boolean.TRUE); }
    public static ParStyle unfolded() { return EMPTY.updateFold(Boolean.FALSE); }

    final Optional<TextAlignment> alignment;
    final Optional<Color> backgroundColor;
    final Optional<Indent> indent;
    final int foldCount;

    private ParStyle() {
        this(Optional.empty(), Optional.empty(), Optional.empty(), 0);
    }

    private ParStyle(Optional<TextAlignment> alignment, Optional<Color> backgroundColor, Optional<Indent> indent, int folds) {
        this.alignment = alignment;
        this.backgroundColor = backgroundColor;
        this.foldCount = folds;
        this.indent = indent;
    }

    @Override
    public int hashCode() {
        return Objects.hash(alignment, backgroundColor, indent, foldCount);
    }

    @Override
    public boolean equals(Object other) {
        if(other instanceof ParStyle that) {
            return Objects.equals(this.alignment, that.alignment) &&
                    Objects.equals(this.backgroundColor, that.backgroundColor) &&
                    Objects.equals(this.indent, that.indent) &&
                    this.foldCount == that.foldCount;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return toCss();
    }

    public String toCss() {
        StringBuilder sb = new StringBuilder();

        alignment.ifPresent(al -> {
            String cssAlignment;
            switch(al) {
                case LEFT:    cssAlignment = "left";    break;
                case CENTER:  cssAlignment = "center";  break;
                case RIGHT:   cssAlignment = "right";   break;
                case JUSTIFY: cssAlignment = "justify"; break;
                default: throw new AssertionError("unreachable code");
            }
            sb.append("-fx-text-alignment: " + cssAlignment + ";");
        });

        backgroundColor.ifPresent(color -> {
            sb.append("-fx-background-color: " + TextStyle.cssColor(color) + ";");
        });

        if ( foldCount > 0 ) sb.append( "visibility: collapse;" );

        return sb.toString();
    }

    public ParStyle updateWith(ParStyle mixin) {
        return new ParStyle(
                mixin.alignment.isPresent() ? mixin.alignment : alignment,
                mixin.backgroundColor.isPresent() ? mixin.backgroundColor : backgroundColor,
                mixin.indent.isPresent() ? mixin.indent : indent,
                mixin.foldCount + foldCount);
    }

    public ParStyle updateAlignment(TextAlignment alignment) {
        return new ParStyle(Optional.of(alignment), backgroundColor, indent, foldCount);
    }

    public ParStyle updateBackgroundColor(Color backgroundColor) {
        return new ParStyle(alignment, Optional.of(backgroundColor), indent, foldCount);
    }

    public ParStyle updateIndent(Indent indent) {
        return new ParStyle(alignment, backgroundColor, Optional.ofNullable(indent), foldCount);
    }

    public ParStyle increaseIndent() {
        return updateIndent( indent.map( Indent::increase ).orElseGet( Indent::new ) );
    }

    public ParStyle decreaseIndent() {
        return updateIndent( indent.filter( in -> in.level > 1 )
                .map( Indent::decrease ).orElse( null ) );
    }

    public Indent getIndent() {
        return indent.get();
    }

    public boolean isIndented() {
        return indent.map( in -> in.level > 0 ).orElse( false );
    }

    public ParStyle updateFold(boolean fold) {
        int foldLevels = fold ? foldCount+1 : Math.max( 0, foldCount-1 );
        return new ParStyle(alignment, backgroundColor, indent, foldLevels);
    }

    public boolean isFolded() {
        return foldCount > 0;
    }
}
