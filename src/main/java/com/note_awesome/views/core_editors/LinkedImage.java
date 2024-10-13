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
import org.fxmisc.richtext.model.Codec;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface LinkedImage {
    static <S> Codec<LinkedImage> codec() {
        return new Codec<LinkedImage>() {
            @Override
            public String getName() {
                return "LinkedImage";
            }

            @Override
            public void encode(DataOutputStream os, LinkedImage linkedImage) throws IOException {
                if (linkedImage.isReal()) {
                    os.writeBoolean(true);
                    String externalPath = linkedImage.getImagePath().replace("\\", "/");
                    Codec.STRING_CODEC.encode(os, externalPath);
                } else {
                    os.writeBoolean(false);
                }
            }

            @Override
            public LinkedImage decode(DataInputStream is) throws IOException {
                if (is.readBoolean()) {
                    String imagePath = Codec.STRING_CODEC.decode(is);
                    imagePath = imagePath.replace("\\",  "/");
                    return new RealLinkedImage(imagePath);
                } else {
                    return new EmptyLinkedImage();
                }
            }
        };
    }

    boolean isReal();

    /**
     * @return The path of the image to render.
     */
    String getImagePath();

    Node createNode();
}
