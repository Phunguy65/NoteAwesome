//package com.mediaplayerulti.mediaplayerulti;
//
//import javafx.beans.binding.BooleanBinding;
//import javafx.fxml.FXML;
//import javafx.scene.Node;
//import javafx.scene.control.*;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.VBox;
//import javafx.scene.paint.Color;
//import javafx.scene.text.TextAlignment;
//import org.fxmisc.flowless.VirtualizedScrollPane;
//import org.fxmisc.richtext.GenericStyledArea;
//import org.fxmisc.richtext.StyledTextArea;
//import org.fxmisc.richtext.TextExt;
//import org.fxmisc.richtext.model.*;
//import org.reactfx.util.Either;
//
//import java.io.DataInputStream;
//import java.io.DataOutputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.Objects;
//import java.util.function.BiConsumer;
//import java.util.function.Function;
//import java.util.function.Predicate;
//import java.util.function.UnaryOperator;
//
//import static javafx.scene.text.TextAlignment.*;
//
//
//public class NoteEditor {
//    private final FoldableStyledArea _area = new FoldableStyledArea();
//    
//    @FXML
//    private VBox vbox;
//    
//    static class FoldableStyledArea extends GenericStyledArea<ParStyle, Either<String, LinkedImage>, TextStyle> {
//        private final static TextOps<String, TextStyle> _styledTextOps = new TextOpsImpl();
//        private final static LinkedImageOps<TextStyle> _linkedImageOps = new LinkedImageOps<>();
//
//        public FoldableStyledArea() {
//            super(ParStyle.EMPTY,
//                    (paragraph, style) -> paragraph.setStyle(style.toCss()),
//                    TextStyle.EMPTY.updateBold(false),
//                    _styledTextOps.or(_linkedImageOps, (s1, s2) -> Optional.empty()),
//                    seg -> createNode(seg, (text, style) -> text.setStyle(style.toCss()))
//
//        }
//
//        public void foldParagraphs(int startPar, int endPar) {
//            foldParagraphs(startPar, endPar, getAddFoldStyle());
//        }
//
//        public foldSelectedParagraphs() {
//            foldParagraphs(getAddFoldStyle());
//        }
//
//        public void unfoldParagraphs(int startingFromPar) {
//            unfoldParagraphs(startingFromPar, getFoldStyleCheck(), getRemoveFoldStyle());
//        }
//
//        public void unfoldText(int startingFromPos) {
//            startingFromPos = offsetToPosition(startingFromPos, Bias.Backward).getMajor();
//            unfoldParagraphs(startingFromPos, getFoldStyleCheck(), getRemoveFoldStyle());
//        }
//
//        protected UnaryOperator<ParStyle> getAddFoldStyle() {
//            return parStyle -> parStyle.updateFold(true);
//        }
//
//        protected UnaryOperator<ParStyle> getRemoveFoldStyle() {
//            return parStyle -> parStyle.updateFold(false);
//        }
//
//        protected Predicate<ParStyle> getFoldStyleCheck() {
//            return parStyle -> parStyle.isFolded();
//        }
//
//        private static Node createNode(StyledSegment<Either<String, LinkedImage>, TextStyle> seg, BiConsumer<? super TextExt, TextStyle>) {
//            return seg.getSegment().unify(
//                    text -> StyledTextArea.createStyledTextNode(text, seg.getStyle(), applyStyle),
//                    LinkedImage::createNode);
//        }
//    }
//
//    private Button createButton(String styleClass, Runnable action, String tooltip) {
//        Button button = new Button();
//        button.getStyleClass().add(styleClass);
//        button.setOnAction(evt -> {
//            action.run();
//            _area.requestFocus();
//        });
//
//        button.setPrefWidth(30);
//        button.setPrefHeight(30);
//
//        if (tooltip != null) {
//            button.setTooltip(new Tooltip(tooltip));
//        }
//
//        return button;
//    }
//
//    private void updateStyleInSelection(Function<StyleSpans<TextStyle>, TextStyle> mixinGetter) {
//        IndexRange selection = _area.getSelection();
//        if (selection.getLength() != 0) {
//            StyleSpans<TextStyle> styles = _area.getStyleSpans(selection);
//            TextStyle mixin = mixinGetter.apply(styles);
//            StyleSpans<TextStyle> newStyles = styles.mapStyles(style -> style.updateWith(mixin));
//            _area.setStyleSpans(selection.getStart(), newStyles);
//        }
//    }
//
//    private void toggleBold() {
//        updateStyleInSelection(spans -> TextStyle.bold(!spans.styleStream().allMatch(style -> style._bold.orElse(false))));
//    }
//
//    private void toggleItalic() {
//        updateStyleInSelection(spans -> {
//            return TextStyle.italic(!spans.styleStream().allMatch(style -> style._italic.orElse(false)));
//        });
//    }
//
//    private void toggleUnderline() {
//        updateStyleInSelection(spans -> {
//            return TextStyle.underline(!spans.styleStream().allMatch(style -> style._underline.orElse(false)));
//        });
//    }
//
//    private void toggleStrikethrough() {
//        updateStyleInSelection(spans -> {
//            return TextStyle.strikethrough(!spans.styleStream().allMatch(style -> style._strikethrough.orElse(false)));
//        });
//    }
//
//    public NoteEditor() {
//        Button undoBtn = createButton("undo-button", _area::undo, "Undo");
//        Button redoBtn = createButton("redo-button", _area::redo, "Redo");
//        Button boldBtn = createButton("bold-button", _area::toggleBold, "Bold");
//        Button italicBtn = createButton("italic-button", _area::toggleItalic, "Italic");
//        Button underlineBtn = createButton("underline-button", _area::toggleUnderline, "Underline");
//        Button strikeBtn = createButton("strike-button", _area::toggleStrikethrough, "Strikethrough");
//
//        undoBtn.disableProperty().bind(_area.undoAvailableProperty().map(x -> !x));
//        redoBtn.disableProperty().bind(_area.redoAvailableProperty().map(x -> !x));
//
//        BooleanBinding selectionEmpty = new BooleanBinding() {
//            {
//                bind(_area.selectionProperty());
//            }
//
//            @Override
//            protected boolean computeValue() {
//                return _area.getSelection().getLength() == 0;
//            }
//        };
//
//        _area.beingUpdatedProperty().addListener((obs, old, beingUpdated) -> {
//            if (!beingUpdated) {
//                boolean bold, italic, underline, strikethrough;
//
//                IndexRange selection = _area.getSelection();
//
//                if (selection.getLength() == 0) {
//                    StyleSpans<TextStyle> styles = _area.getStyleSpans(selection);
//
//                    bold = styles.styleStream().allMatch(style -> style._bold.orElse(false));
//                    italic = styles.styleStream().allMatch(style -> style._italic.orElse(false));
//                    underline = styles.styleStream().allMatch(style -> style._underline.orElse(false));
//                    strikethrough = styles.styleStream().allMatch(style -> style._strikethrough.orElse(false));
//                } else {
//                    int paragraph = _area.getCurrentParagraph();
//                    int column = _area.getCaretColumn();
//                    TextStyle style = _area.getStyleAtPosition(paragraph, column);
//                    bold = style._bold.orElse(false);
//                    italic = style._italic.orElse(false);
//                    underline = style._underline.orElse(false);
//                }
//                int startPar = _area.offsetToPosition(selection.getStart(), TwoDimensional.Bias.Forward).getMajor();
//                int endPar = _area.offsetToPosition(selection.getEnd(), TwoDimensional.Bias.Backward).getMajor();
//                List<Paragraph<ParStyle, Either<String, LinkedImage>, TextStyle>> pars = _area.getParagraphs().subList(startPar, endPar + 1);
//            }    
//        });
//
//        ToolBar toolbar = new ToolBar(
//            undoBtn,
//            redoBtn,
//            new Separator(),
//            boldBtn,
//            italicBtn,
//            underlineBtn,
//            strikeBtn
//        );
//
//        VirtualizedScrollPane<GenericStyledArea<ParStyle, Either<String, LinkedImage>, TextStyle>> vsPane = new VirtualizedScrollPane<>(_area);
//        
//        _vbox = new VBox(toolbar, vsPane);
//        
//        
//        _area.setWrapText(true);
//        _area.requestFocus();
//
//    }
//
//}
//
//public class TextStyle {
//    public static final TextStyle EMPTY = new TextStyle();
//
//    public static final Codec<TextStyle> CODEC = new Codec<TextStyle>() {
//
//
//        private final Codec<Optional<String>> OPT_STRING_CODEC =
//                Codec.optionalCodec(Codec.STRING_CODEC);
//        private final Codec<Optional<Color>> OPT_COLOR_CODEC =
//                Codec.optionalCodec(Codec.COLOR_CODEC);
//        @Override
//        public String getName() {
//            return "text-style";
//        }
//
//        @Override
//        public void encode(DataOutputStream os, TextStyle s) throws IOException {
//            os.writeByte(encodeBoldItalicUnderlineStrikethrough(s));
//        }
//        
//        @Override
//        public TextStyle decode(DataInputStream is) throws IOException {
//            byte bius = is.readByte();
//            return new TextStyle(
//                    bold(bius),
//                    italic(bius),
//                    underline(bius),
//                    strikethrough(bius)
//            );
//        }
//        
//        private Optional<Boolean> decodeOptionalBoolean(int i) throws IOException {
//            switch (i) {
//                case 0:
//                    return Optional.empty();
//                case 1:
//                    return Optional.of(Boolean.FALSE);
//                case 2:
//                    return Optional.of(Boolean.TRUE);
//                default:
//                    throw new IOException("Invalid boolean value: " + i);
//            }
//        }
//        
//        private int encodeOptionalUnit(Optional<Integer> oi){
//            return oi.orElse(-1);
//        }
//        
//        private int encodeOptionalBoolean(Optional<Boolean> ob) {
//            return ob.map(b -> 2 + (b ? 1 : 0)).orElse(0);
//        }
//        
//        private Optional<Integer> decodeOptionalUnit(int i) {
//            return i < 0 ? Optional.empty() : Optional.of(i);
//        }
//        
//        private Optional<Boolean> bold(byte bius) throws IOException {
//            return decodeOptionalBoolean((bius >> 6) & 3);
//        }
//        
//        private Optional<Boolean> italic(byte bius) throws IOException {
//            return decodeOptionalBoolean((bius >> 4) & 3);
//        }
//        
//        private Optional<Boolean> underline(byte bius) throws IOException {
//            return decodeOptionalBoolean((bius >> 2) & 3);
//        }
//        
//        private Optional<Boolean> strikethrough(byte bius) throws IOException {
//            return decodeOptionalBoolean((bius >> 0) & 3);
//        }
//        
//        private int encodeBoldItalicUnderlineStrikethrough(TextStyle s){
//            return encodeOptionalBoolean(s._bold) << 6 |
//                    encodeOptionalBoolean(s._italic) << 4 |
//                    encodeOptionalBoolean(s._underline) << 2 |
//                    encodeOptionalBoolean(s._strikethrough);
//        }
//        
//};
//    public static TextStyle bold(boolean bold) {
//        return EMPTY.updateBold(bold);
//    }
//
//    public TextStyle updateBold(boolean bold) {
//        return new TextStyle(Optional.of(bold), Optional.empty(), Optional.empty(), Optional.empty());
//    }
//
//    final Optional<Boolean> _bold;
//
//    public static TextStyle italic(boolean italic) {
//        return EMPTY.updateItalic(italic);
//    }
//
//    public TextStyle updateItalic(boolean italic) {
//        return new TextStyle(Optional.empty(), Optional.of(italic), Optional.empty(), Optional.empty());
//    }
//
//    final Optional<Boolean> _italic;
//
//    public static TextStyle underline(boolean underline) {
//        return EMPTY.updateUnderline(underline);
//    }
//
//    public TextStyle updateUnderline(boolean underline) {
//        return new TextStyle(Optional.empty(), Optional.empty(), Optional.of(underline), Optional.empty());
//    }
//
//    final Optional<Boolean> _underline;
//
//    public static TextStyle strikethrough(boolean strikethrough) {
//        return EMPTY.updateStrikethrough(strikethrough);
//    }
//
//    public TextStyle updateStrikethrough(boolean strikethrough) {
//        return new TextStyle(Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(strikethrough));
//    }
//
//    final Optional<Boolean> _strikethrough;
//
//    public TextStyle() {
//        this(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
//    }  
//
//    public TextStyle(Optional<Boolean> bold, Optional<Boolean> italic, Optional<Boolean> underline ,Optional<Boolean>_strikethrough) {
//        this._bold = bold;
//        this._italic = italic;
//        this._underline = underline;
//        this._strikethrough = _strikethrough;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(_bold, _italic, _underline, _strikethrough);
//    }
//
//    @Override
//    public boolean equals(Objects other) {
//        if (other instanceof TextStyle) {
//            TextStyle that = (TextStyle) other;
//            return Objects.equals(this._bold, that._bold) &&
//                    Objects.equals(this._italic, that._italic) &&
//                    Objects.equals(this._underline, that._underline) &&
//                    Objects.equals(this._strikethrough, that._strikethrough);
//        }
//        return false;
//    }
//
//    @Override
//    public String toString() {
//        List<String> styles = new ArrayList<>();
//
//        _bold.ifPresent(b -> styles.add(b.toString()));
//        _italic.ifPresent(i -> styles.add(i.toString()));
//        _underline.ifPresent(u -> styles.add(u.toString()));
//        _strikethrough.ifPresent(s -> styles.add(s.toString()));
//
//        return String.join(", ", styles);
//    }
//
//    public String toCss() {
//        StringBuilder css = new StringBuilder();
//
//        if (_bold.isPresent()) {
//            if (_bold.get()) {
//                css.append("-fx-font-weight: bold;");
//            } else {
//                css.append("-fx-font-weight: normal;");
//            }
//        }
//
//        if (_italic.isPresent()) {
//            if (_italic.get()) {
//                css.append("-fx-font-style: italic;");
//            } else {
//                css.append("-fx-font-style: normal;");
//            }
//        }
//
//        if (_underline.isPresent()) {
//            if (_underline.get()) {
//                css.append("-fx-underline: true;");
//            } else {
//                css.append("-fx-underline: false;");
//            }
//        }
//
//        if (_strikethrough.isPresent()) {
//            if (_strikethrough.get()) {
//                css.append("-fx-strikethrough: true;");
//            } else {
//                css.append("-fx-strikethrough: false;");
//            }
//        }
//    }
//
//    public TextStyle updateWith(TextStyle other) {
//        return new TextStyle(
//                other._bold.isPresent() ? other._bold : _bold,
//                other._italic.isPresent() ? other._italic : _italic,
//                other._underline.isPresent() ? other._underline : _underline,
//                other._strikethrough.isPresent() ? other._strikethrough : _strikethrough
//        );
//    }
//};
//    
//
//public class ParStyle {
//    public static final ParStyle EMPTY = new ParStyle();
//
//    public static final Codec<ParStyle> CODEC = new Codec<ParStyle>() {
//
//        private final Codec<Optional<TextAlignment>> OPT_ALIGNMENT_CODEC = Codec.optionalCodec(Codec.enumCodec(TextAlignment.class));
//
//        private final Codec<Optional<Color>> OPT_COLOR_CODEC = Codec.optionalCodec(Codec.COLOR_CODEC);
//
//        @Override
//        public String getName() {
//            return "par-style";
//        }
//
//        @Override
//        public void encode(DataOutputStream os, ParStyle s) throws IOException {
//            OPT_ALIGNMENT_CODEC.encode(os, s.alignment);
//            OPT_COLOR_CODEC.encode(os, s.backgroundColor);
//            os.writeInt(s.indent.map(i -> Integer.valueOf(i.level)).orElse(0));
//            os.writeInt(s.foldCount);
//        }
//
//        @Override
//        public ParStyle decode(DataInputStream dataInputStream) throws IOException {
//            return new ParStyle(
//                    OPT_ALIGNMENT_CODEC.decode(dataInputStream),
//                    OPT_COLOR_CODEC.decode(dataInputStream),
//                    Optional.of(new Indent(dataInputStream.readInt())),
//                    dataInputStream.readInt());
//        }
//    };
//
//    public static ParStyle alignLeft() {
//        return EMPTY.updateAlignment(LEFT);
//    }
//
//    public static ParStyle alignCenter() {
//        return EMPTY.updateAlignment(CENTER);
//    }
//
//    public static ParStyle alignRight() {
//        return EMPTY.updateAlignment(RIGHT);
//    }
//
//    public static ParStyle alignJustify() {
//        return EMPTY.updateAlignment(JUSTIFY);
//    }
//
//    public static ParStyle backgroundColor(Color color) {
//        return EMPTY.updateBackgroundColor(color);
//    }
//
//    public static ParStyle folded() {
//        return EMPTY.updateFold(Boolean.TRUE);
//    }
//
//    public static ParStyle unfolded() {
//        return EMPTY.updateFold(Boolean.FALSE);
//    }
//
//    final Optional<TextAlignment> alignment;
//    final Optional<Color> backgroundColor;
//    final Optional<Indent> indent;
//    final int foldCount;
//
//    private ParStyle() {
//        this(Optional.empty(), Optional.empty(), Optional.empty(), 0);
//    }
//
//    private ParStyle(Optional<TextAlignment> alignment, Optional<Color> backgroundColor, Optional<Indent> indent, int folds) {
//        this.alignment = alignment;
//        this.backgroundColor = backgroundColor;
//        this.foldCount = folds;
//        this.indent = indent;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(alignment, backgroundColor, indent, foldCount);
//    }
//
//    @Override
//    public boolean equals(Object other) {
//        if (other instanceof ParStyle) {
//            ParStyle that = (ParStyle) other;
//            return Objects.equals(this.alignment, that.alignment) &&
//                    Objects.equals(this.backgroundColor, that.backgroundColor) &&
//                    Objects.equals(this.indent, that.indent) &&
//                    this.foldCount == that.foldCount;
//        } else {
//            return false;
//        }
//    }
//
//    @Override
//    public String toString() {
//        return toCss();
//    }
//
//    public String toCss() {
//        StringBuilder sb = new StringBuilder();
//
//        alignment.ifPresent(al -> {
//            String cssAlignment = switch (al) {
//                case LEFT -> "left";
//                case CENTER -> "center";
//                case RIGHT -> "right";
//                case JUSTIFY -> "justify";
//                default -> throw new AssertionError("unreachable code");
//            };
//            sb.append("-fx-text-alignment: ").append(cssAlignment).append(";");
//        });
//
//        backgroundColor.ifPresent(color -> {
//            sb.append("-fx-background-color: " + TextStyle.colorToCss(color) + ";");
//        });
//
//        if (foldCount > 0) sb.append("visibility: collapse;");
//
//        return sb.toString();
//    }
//
//    public ParStyle updateWith(ParStyle mixin) {
//        return new ParStyle(
//                mixin.alignment.isPresent() ? mixin.alignment : alignment,
//                mixin.backgroundColor.isPresent() ? mixin.backgroundColor : backgroundColor,
//                mixin.indent.isPresent() ? mixin.indent : indent,
//                mixin.foldCount + foldCount);
//    }
//
//    public ParStyle updateAlignment(TextAlignment alignment) {
//        return new ParStyle(Optional.of(alignment), backgroundColor, indent, foldCount);
//    }
//
//    public ParStyle updateBackgroundColor(Color backgroundColor) {
//        return new ParStyle(alignment, Optional.of(backgroundColor), indent, foldCount);
//    }
//
//    public ParStyle updateIndent(Indent indent) {
//        return new ParStyle(alignment, backgroundColor, Optional.ofNullable(indent), foldCount);
//    }
//
//    public ParStyle increaseIndent() {
//        return updateIndent(indent.map(Indent::increase).orElseGet(Indent::new));
//    }
//
//    public ParStyle decreaseIndent() {
//        return updateIndent(indent.filter(in -> in.level > 1)
//                .map(Indent::decrease).orElse(null));
//    }
//
//    public Indent getIndent() {
//        return indent.get();
//    }
//
//    public boolean isIndented() {
//        return indent.map(in -> in.level > 0).orElse(false);
//    }
//
//    public ParStyle updateFold(boolean fold) {
//        int foldLevels = fold ? foldCount + 1 : Math.max(0, foldCount - 1);
//        return new ParStyle(alignment, backgroundColor, indent, foldLevels);
//    }
//
//    public boolean isFolded() {
//        return foldCount > 0;
//    }
//};
//
//interface LinkedImage {
//    static <S> Codec<LinkedImage> codec() {
//        return new Codec<LinkedImage>() {
//            @Override
//            public String getName() {
//                return "LinkedImage";
//            }
//
//            @Override
//            public void encode(DataOutputStream os, LinkedImage linkedImage) throws IOException {
//                if (linkedImage.isReal()) {
//                    os.writeBoolean(true);
//                    String externalPath = linkedImage.getImagePath().replace("\\", "/");
//                    Codec.STRING_CODEC.encode(os, externalPath);
//                } else {
//                    os.writeBoolean(false);
//                }
//            }
//
//            @Override
//            public LinkedImage decode(DataInputStream is) throws IOException {
//                if (is.readBoolean()) {
//                    String imagePath = Codec.STRING_CODEC.decode(is);
//                    imagePath = imagePath.replace("\\", "/");
//                    return new RealLinkedImage(imagePath);
//                } else {
//                    return new EmptyLinkedImage();
//                }
//            }
//        };
//    }
//
//    boolean isReal();
//
//    /**
//     * @return The path of the image to render.
//     */
//    String getImagePath();
//
//    Node createNode();
//}
//
//class EmptyLinkedImage implements LinkedImage {
//    @Override
//    public boolean isReal() {
//        return false;
//    }
//
//    @Override
//    public String getImagePath() {
//        throw new UnsupportedOperationException("EmptyLinkedImage does not have an image path.");
//    }
//
//    @Override
//    public Node createNode() {
//        throw new UnsupportedOperationException("EmptyLinkedImage does not have an image path.");
//    }
//};
//
//class LinkedImageOps<S> extends NodeSegmentOpsBase<LinkedImage, S> {
//    public LinkedImageOps() {
//        super(new EmptyLinkedImage());
//    }
//
//    @Override
//    public int length(LinkedImage linkedImage) {
//        return linkedImage.isReal() ? 1 : 0;
//    }
//}
//
//
//class Indent
//{
//    double width = 15;
//    int level = 1;
//
//    Indent() {}
//
//    Indent( int level )
//    {
//        if ( level > 0 ) this.level = level;
//    }
//
//    Indent increase()
//    {
//        return new Indent( level+1 );
//    }
//
//    Indent decrease()
//    {
//        return new Indent( level-1 );
//    }
//
//    int getLevel() { return level; }
//
//    @Override
//    public String toString()
//    {
//        return "indent: "+ level;
//    }
//}
//
//public class RealLinkedImage implements LinkedImage {
//
//    private final String imagePath;
//
//    /**
//     * Creates a new linked image object.
//     *
//     * @param imagePath The path to the image file.
//     */
//    public RealLinkedImage(String imagePath) {
//
//        // if the image is below the current working directory,
//        // then store as relative path name.
//        String currentDir = System.getProperty("user.dir") + File.separatorChar;
//        if (imagePath.startsWith(currentDir)) {
//            imagePath = imagePath.substring(currentDir.length());
//        }
//
//        this.imagePath = imagePath;
//    }
//
//    @Override
//    public boolean isReal() {
//        return true;
//    }
//
//    @Override
//    public String getImagePath() {
//        return imagePath;
//    }
//
//    @Override
//    public String toString() {
//        return String.format("RealLinkedImage[path=%s]", imagePath);
//    }
//
//    @Override
//    public Node createNode() {
//        Image image = new Image("file:" + imagePath); // XXX: No need to create new Image objects each time -
//        // could be cached in the model layer
//        ImageView result = new ImageView(image);
//        return result;
//    }
//}package com.mediaplayerulti.mediaplayerulti;
//
//import javafx.beans.binding.BooleanBinding;
//import javafx.fxml.FXML;
//import javafx.scene.Node;
//import javafx.scene.control.*;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.VBox;
//import javafx.scene.paint.Color;
//import javafx.scene.text.TextAlignment;
//import org.fxmisc.flowless.VirtualizedScrollPane;
//import org.fxmisc.richtext.GenericStyledArea;
//import org.fxmisc.richtext.StyledTextArea;
//import org.fxmisc.richtext.TextExt;
//import org.fxmisc.richtext.model.*;
//import org.reactfx.util.Either;
//
//import java.io.DataInputStream;
//import java.io.DataOutputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.Objects;
//import java.util.function.BiConsumer;
//import java.util.function.Function;
//import java.util.function.Predicate;
//import java.util.function.UnaryOperator;
//
//import static javafx.scene.text.TextAlignment.*;
//
//
//public class NoteEditor {
//    private final FoldableStyledArea _area = new FoldableStyledArea();
//    
//    @FXML
//    private VBox vbox;
//    
//    static class FoldableStyledArea extends GenericStyledArea<ParStyle, Either<String, LinkedImage>, TextStyle> {
//        private final static TextOps<String, TextStyle> _styledTextOps = new TextOpsImpl();
//        private final static LinkedImageOps<TextStyle> _linkedImageOps = new LinkedImageOps<>();
//
//        public FoldableStyledArea() {
//            super(ParStyle.EMPTY,
//                    (paragraph, style) -> paragraph.setStyle(style.toCss()),
//                    TextStyle.EMPTY.updateBold(false),
//                    _styledTextOps.or(_linkedImageOps, (s1, s2) -> Optional.empty()),
//                    seg -> createNode(seg, (text, style) -> text.setStyle(style.toCss()))
//
//        }
//
//        public void foldParagraphs(int startPar, int endPar) {
//            foldParagraphs(startPar, endPar, getAddFoldStyle());
//        }
//
//        public foldSelectedParagraphs() {
//            foldParagraphs(getAddFoldStyle());
//        }
//
//        public void unfoldParagraphs(int startingFromPar) {
//            unfoldParagraphs(startingFromPar, getFoldStyleCheck(), getRemoveFoldStyle());
//        }
//
//        public void unfoldText(int startingFromPos) {
//            startingFromPos = offsetToPosition(startingFromPos, Bias.Backward).getMajor();
//            unfoldParagraphs(startingFromPos, getFoldStyleCheck(), getRemoveFoldStyle());
//        }
//
//        protected UnaryOperator<ParStyle> getAddFoldStyle() {
//            return parStyle -> parStyle.updateFold(true);
//        }
//
//        protected UnaryOperator<ParStyle> getRemoveFoldStyle() {
//            return parStyle -> parStyle.updateFold(false);
//        }
//
//        protected Predicate<ParStyle> getFoldStyleCheck() {
//            return parStyle -> parStyle.isFolded();
//        }
//
//        private static Node createNode(StyledSegment<Either<String, LinkedImage>, TextStyle> seg, BiConsumer<? super TextExt, TextStyle>) {
//            return seg.getSegment().unify(
//                    text -> StyledTextArea.createStyledTextNode(text, seg.getStyle(), applyStyle),
//                    LinkedImage::createNode);
//        }
//    }
//
//    private Button createButton(String styleClass, Runnable action, String tooltip) {
//        Button button = new Button();
//        button.getStyleClass().add(styleClass);
//        button.setOnAction(evt -> {
//            action.run();
//            _area.requestFocus();
//        });
//
//        button.setPrefWidth(30);
//        button.setPrefHeight(30);
//
//        if (tooltip != null) {
//            button.setTooltip(new Tooltip(tooltip));
//        }
//
//        return button;
//    }
//
//    private void updateStyleInSelection(Function<StyleSpans<TextStyle>, TextStyle> mixinGetter) {
//        IndexRange selection = _area.getSelection();
//        if (selection.getLength() != 0) {
//            StyleSpans<TextStyle> styles = _area.getStyleSpans(selection);
//            TextStyle mixin = mixinGetter.apply(styles);
//            StyleSpans<TextStyle> newStyles = styles.mapStyles(style -> style.updateWith(mixin));
//            _area.setStyleSpans(selection.getStart(), newStyles);
//        }
//    }
//
//    private void toggleBold() {
//        updateStyleInSelection(spans -> TextStyle.bold(!spans.styleStream().allMatch(style -> style._bold.orElse(false))));
//    }
//
//    private void toggleItalic() {
//        updateStyleInSelection(spans -> {
//            return TextStyle.italic(!spans.styleStream().allMatch(style -> style._italic.orElse(false)));
//        });
//    }
//
//    private void toggleUnderline() {
//        updateStyleInSelection(spans -> {
//            return TextStyle.underline(!spans.styleStream().allMatch(style -> style._underline.orElse(false)));
//        });
//    }
//
//    private void toggleStrikethrough() {
//        updateStyleInSelection(spans -> {
//            return TextStyle.strikethrough(!spans.styleStream().allMatch(style -> style._strikethrough.orElse(false)));
//        });
//    }
//
//    public NoteEditor() {
//        Button undoBtn = createButton("undo-button", _area::undo, "Undo");
//        Button redoBtn = createButton("redo-button", _area::redo, "Redo");
//        Button boldBtn = createButton("bold-button", _area::toggleBold, "Bold");
//        Button italicBtn = createButton("italic-button", _area::toggleItalic, "Italic");
//        Button underlineBtn = createButton("underline-button", _area::toggleUnderline, "Underline");
//        Button strikeBtn = createButton("strike-button", _area::toggleStrikethrough, "Strikethrough");
//
//        undoBtn.disableProperty().bind(_area.undoAvailableProperty().map(x -> !x));
//        redoBtn.disableProperty().bind(_area.redoAvailableProperty().map(x -> !x));
//
//        BooleanBinding selectionEmpty = new BooleanBinding() {
//            {
//                bind(_area.selectionProperty());
//            }
//
//            @Override
//            protected boolean computeValue() {
//                return _area.getSelection().getLength() == 0;
//            }
//        };
//
//        _area.beingUpdatedProperty().addListener((obs, old, beingUpdated) -> {
//            if (!beingUpdated) {
//                boolean bold, italic, underline, strikethrough;
//
//                IndexRange selection = _area.getSelection();
//
//                if (selection.getLength() == 0) {
//                    StyleSpans<TextStyle> styles = _area.getStyleSpans(selection);
//
//                    bold = styles.styleStream().allMatch(style -> style._bold.orElse(false));
//                    italic = styles.styleStream().allMatch(style -> style._italic.orElse(false));
//                    underline = styles.styleStream().allMatch(style -> style._underline.orElse(false));
//                    strikethrough = styles.styleStream().allMatch(style -> style._strikethrough.orElse(false));
//                } else {
//                    int paragraph = _area.getCurrentParagraph();
//                    int column = _area.getCaretColumn();
//                    TextStyle style = _area.getStyleAtPosition(paragraph, column);
//                    bold = style._bold.orElse(false);
//                    italic = style._italic.orElse(false);
//                    underline = style._underline.orElse(false);
//                }
//                int startPar = _area.offsetToPosition(selection.getStart(), TwoDimensional.Bias.Forward).getMajor();
//                int endPar = _area.offsetToPosition(selection.getEnd(), TwoDimensional.Bias.Backward).getMajor();
//                List<Paragraph<ParStyle, Either<String, LinkedImage>, TextStyle>> pars = _area.getParagraphs().subList(startPar, endPar + 1);
//            }    
//        });
//
//        ToolBar toolbar = new ToolBar(
//            undoBtn,
//            redoBtn,
//            new Separator(),
//            boldBtn,
//            italicBtn,
//            underlineBtn,
//            strikeBtn
//        );
//
//        VirtualizedScrollPane<GenericStyledArea<ParStyle, Either<String, LinkedImage>, TextStyle>> vsPane = new VirtualizedScrollPane<>(_area);
//        
//        _vbox = new VBox(toolbar, vsPane);
//        
//        
//        _area.setWrapText(true);
//        _area.requestFocus();
//
//    }
//
//}
//
//public class TextStyle {
//    public static final TextStyle EMPTY = new TextStyle();
//
//    public static final Codec<TextStyle> CODEC = new Codec<TextStyle>() {
//
//
//        private final Codec<Optional<String>> OPT_STRING_CODEC =
//                Codec.optionalCodec(Codec.STRING_CODEC);
//        private final Codec<Optional<Color>> OPT_COLOR_CODEC =
//                Codec.optionalCodec(Codec.COLOR_CODEC);
//        @Override
//        public String getName() {
//            return "text-style";
//        }
//
//        @Override
//        public void encode(DataOutputStream os, TextStyle s) throws IOException {
//            os.writeByte(encodeBoldItalicUnderlineStrikethrough(s));
//        }
//        
//        @Override
//        public TextStyle decode(DataInputStream is) throws IOException {
//            byte bius = is.readByte();
//            return new TextStyle(
//                    bold(bius),
//                    italic(bius),
//                    underline(bius),
//                    strikethrough(bius)
//            );
//        }
//        
//        private Optional<Boolean> decodeOptionalBoolean(int i) throws IOException {
//            switch (i) {
//                case 0:
//                    return Optional.empty();
//                case 1:
//                    return Optional.of(Boolean.FALSE);
//                case 2:
//                    return Optional.of(Boolean.TRUE);
//                default:
//                    throw new IOException("Invalid boolean value: " + i);
//            }
//        }
//        
//        private int encodeOptionalUnit(Optional<Integer> oi){
//            return oi.orElse(-1);
//        }
//        
//        private int encodeOptionalBoolean(Optional<Boolean> ob) {
//            return ob.map(b -> 2 + (b ? 1 : 0)).orElse(0);
//        }
//        
//        private Optional<Integer> decodeOptionalUnit(int i) {
//            return i < 0 ? Optional.empty() : Optional.of(i);
//        }
//        
//        private Optional<Boolean> bold(byte bius) throws IOException {
//            return decodeOptionalBoolean((bius >> 6) & 3);
//        }
//        
//        private Optional<Boolean> italic(byte bius) throws IOException {
//            return decodeOptionalBoolean((bius >> 4) & 3);
//        }
//        
//        private Optional<Boolean> underline(byte bius) throws IOException {
//            return decodeOptionalBoolean((bius >> 2) & 3);
//        }
//        
//        private Optional<Boolean> strikethrough(byte bius) throws IOException {
//            return decodeOptionalBoolean((bius >> 0) & 3);
//        }
//        
//        private int encodeBoldItalicUnderlineStrikethrough(TextStyle s){
//            return encodeOptionalBoolean(s._bold) << 6 |
//                    encodeOptionalBoolean(s._italic) << 4 |
//                    encodeOptionalBoolean(s._underline) << 2 |
//                    encodeOptionalBoolean(s._strikethrough);
//        }
//        
//};
//    public static TextStyle bold(boolean bold) {
//        return EMPTY.updateBold(bold);
//    }
//
//    public TextStyle updateBold(boolean bold) {
//        return new TextStyle(Optional.of(bold), Optional.empty(), Optional.empty(), Optional.empty());
//    }
//
//    final Optional<Boolean> _bold;
//
//    public static TextStyle italic(boolean italic) {
//        return EMPTY.updateItalic(italic);
//    }
//
//    public TextStyle updateItalic(boolean italic) {
//        return new TextStyle(Optional.empty(), Optional.of(italic), Optional.empty(), Optional.empty());
//    }
//
//    final Optional<Boolean> _italic;
//
//    public static TextStyle underline(boolean underline) {
//        return EMPTY.updateUnderline(underline);
//    }
//
//    public TextStyle updateUnderline(boolean underline) {
//        return new TextStyle(Optional.empty(), Optional.empty(), Optional.of(underline), Optional.empty());
//    }
//
//    final Optional<Boolean> _underline;
//
//    public static TextStyle strikethrough(boolean strikethrough) {
//        return EMPTY.updateStrikethrough(strikethrough);
//    }
//
//    public TextStyle updateStrikethrough(boolean strikethrough) {
//        return new TextStyle(Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(strikethrough));
//    }
//
//    final Optional<Boolean> _strikethrough;
//
//    public TextStyle() {
//        this(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
//    }  
//
//    public TextStyle(Optional<Boolean> bold, Optional<Boolean> italic, Optional<Boolean> underline ,Optional<Boolean>_strikethrough) {
//        this._bold = bold;
//        this._italic = italic;
//        this._underline = underline;
//        this._strikethrough = _strikethrough;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(_bold, _italic, _underline, _strikethrough);
//    }
//
//    @Override
//    public boolean equals(Objects other) {
//        if (other instanceof TextStyle) {
//            TextStyle that = (TextStyle) other;
//            return Objects.equals(this._bold, that._bold) &&
//                    Objects.equals(this._italic, that._italic) &&
//                    Objects.equals(this._underline, that._underline) &&
//                    Objects.equals(this._strikethrough, that._strikethrough);
//        }
//        return false;
//    }
//
//    @Override
//    public String toString() {
//        List<String> styles = new ArrayList<>();
//
//        _bold.ifPresent(b -> styles.add(b.toString()));
//        _italic.ifPresent(i -> styles.add(i.toString()));
//        _underline.ifPresent(u -> styles.add(u.toString()));
//        _strikethrough.ifPresent(s -> styles.add(s.toString()));
//
//        return String.join(", ", styles);
//    }
//
//    public String toCss() {
//        StringBuilder css = new StringBuilder();
//
//        if (_bold.isPresent()) {
//            if (_bold.get()) {
//                css.append("-fx-font-weight: bold;");
//            } else {
//                css.append("-fx-font-weight: normal;");
//            }
//        }
//
//        if (_italic.isPresent()) {
//            if (_italic.get()) {
//                css.append("-fx-font-style: italic;");
//            } else {
//                css.append("-fx-font-style: normal;");
//            }
//        }
//
//        if (_underline.isPresent()) {
//            if (_underline.get()) {
//                css.append("-fx-underline: true;");
//            } else {
//                css.append("-fx-underline: false;");
//            }
//        }
//
//        if (_strikethrough.isPresent()) {
//            if (_strikethrough.get()) {
//                css.append("-fx-strikethrough: true;");
//            } else {
//                css.append("-fx-strikethrough: false;");
//            }
//        }
//    }
//
//    public TextStyle updateWith(TextStyle other) {
//        return new TextStyle(
//                other._bold.isPresent() ? other._bold : _bold,
//                other._italic.isPresent() ? other._italic : _italic,
//                other._underline.isPresent() ? other._underline : _underline,
//                other._strikethrough.isPresent() ? other._strikethrough : _strikethrough
//        );
//    }
//};
//    
//
//public class ParStyle {
//    public static final ParStyle EMPTY = new ParStyle();
//
//    public static final Codec<ParStyle> CODEC = new Codec<ParStyle>() {
//
//        private final Codec<Optional<TextAlignment>> OPT_ALIGNMENT_CODEC = Codec.optionalCodec(Codec.enumCodec(TextAlignment.class));
//
//        private final Codec<Optional<Color>> OPT_COLOR_CODEC = Codec.optionalCodec(Codec.COLOR_CODEC);
//
//        @Override
//        public String getName() {
//            return "par-style";
//        }
//
//        @Override
//        public void encode(DataOutputStream os, ParStyle s) throws IOException {
//            OPT_ALIGNMENT_CODEC.encode(os, s.alignment);
//            OPT_COLOR_CODEC.encode(os, s.backgroundColor);
//            os.writeInt(s.indent.map(i -> Integer.valueOf(i.level)).orElse(0));
//            os.writeInt(s.foldCount);
//        }
//
//        @Override
//        public ParStyle decode(DataInputStream dataInputStream) throws IOException {
//            return new ParStyle(
//                    OPT_ALIGNMENT_CODEC.decode(dataInputStream),
//                    OPT_COLOR_CODEC.decode(dataInputStream),
//                    Optional.of(new Indent(dataInputStream.readInt())),
//                    dataInputStream.readInt());
//        }
//    };
//
//    public static ParStyle alignLeft() {
//        return EMPTY.updateAlignment(LEFT);
//    }
//
//    public static ParStyle alignCenter() {
//        return EMPTY.updateAlignment(CENTER);
//    }
//
//    public static ParStyle alignRight() {
//        return EMPTY.updateAlignment(RIGHT);
//    }
//
//    public static ParStyle alignJustify() {
//        return EMPTY.updateAlignment(JUSTIFY);
//    }
//
//    public static ParStyle backgroundColor(Color color) {
//        return EMPTY.updateBackgroundColor(color);
//    }
//
//    public static ParStyle folded() {
//        return EMPTY.updateFold(Boolean.TRUE);
//    }
//
//    public static ParStyle unfolded() {
//        return EMPTY.updateFold(Boolean.FALSE);
//    }
//
//    final Optional<TextAlignment> alignment;
//    final Optional<Color> backgroundColor;
//    final Optional<Indent> indent;
//    final int foldCount;
//
//    private ParStyle() {
//        this(Optional.empty(), Optional.empty(), Optional.empty(), 0);
//    }
//
//    private ParStyle(Optional<TextAlignment> alignment, Optional<Color> backgroundColor, Optional<Indent> indent, int folds) {
//        this.alignment = alignment;
//        this.backgroundColor = backgroundColor;
//        this.foldCount = folds;
//        this.indent = indent;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(alignment, backgroundColor, indent, foldCount);
//    }
//
//    @Override
//    public boolean equals(Object other) {
//        if (other instanceof ParStyle) {
//            ParStyle that = (ParStyle) other;
//            return Objects.equals(this.alignment, that.alignment) &&
//                    Objects.equals(this.backgroundColor, that.backgroundColor) &&
//                    Objects.equals(this.indent, that.indent) &&
//                    this.foldCount == that.foldCount;
//        } else {
//            return false;
//        }
//    }
//
//    @Override
//    public String toString() {
//        return toCss();
//    }
//
//    public String toCss() {
//        StringBuilder sb = new StringBuilder();
//
//        alignment.ifPresent(al -> {
//            String cssAlignment = switch (al) {
//                case LEFT -> "left";
//                case CENTER -> "center";
//                case RIGHT -> "right";
//                case JUSTIFY -> "justify";
//                default -> throw new AssertionError("unreachable code");
//            };
//            sb.append("-fx-text-alignment: ").append(cssAlignment).append(";");
//        });
//
//        backgroundColor.ifPresent(color -> {
//            sb.append("-fx-background-color: " + TextStyle.colorToCss(color) + ";");
//        });
//
//        if (foldCount > 0) sb.append("visibility: collapse;");
//
//        return sb.toString();
//    }
//
//    public ParStyle updateWith(ParStyle mixin) {
//        return new ParStyle(
//                mixin.alignment.isPresent() ? mixin.alignment : alignment,
//                mixin.backgroundColor.isPresent() ? mixin.backgroundColor : backgroundColor,
//                mixin.indent.isPresent() ? mixin.indent : indent,
//                mixin.foldCount + foldCount);
//    }
//
//    public ParStyle updateAlignment(TextAlignment alignment) {
//        return new ParStyle(Optional.of(alignment), backgroundColor, indent, foldCount);
//    }
//
//    public ParStyle updateBackgroundColor(Color backgroundColor) {
//        return new ParStyle(alignment, Optional.of(backgroundColor), indent, foldCount);
//    }
//
//    public ParStyle updateIndent(Indent indent) {
//        return new ParStyle(alignment, backgroundColor, Optional.ofNullable(indent), foldCount);
//    }
//
//    public ParStyle increaseIndent() {
//        return updateIndent(indent.map(Indent::increase).orElseGet(Indent::new));
//    }
//
//    public ParStyle decreaseIndent() {
//        return updateIndent(indent.filter(in -> in.level > 1)
//                .map(Indent::decrease).orElse(null));
//    }
//
//    public Indent getIndent() {
//        return indent.get();
//    }
//
//    public boolean isIndented() {
//        return indent.map(in -> in.level > 0).orElse(false);
//    }
//
//    public ParStyle updateFold(boolean fold) {
//        int foldLevels = fold ? foldCount + 1 : Math.max(0, foldCount - 1);
//        return new ParStyle(alignment, backgroundColor, indent, foldLevels);
//    }
//
//    public boolean isFolded() {
//        return foldCount > 0;
//    }
//};
//
//interface LinkedImage {
//    static <S> Codec<LinkedImage> codec() {
//        return new Codec<LinkedImage>() {
//            @Override
//            public String getName() {
//                return "LinkedImage";
//            }
//
//            @Override
//            public void encode(DataOutputStream os, LinkedImage linkedImage) throws IOException {
//                if (linkedImage.isReal()) {
//                    os.writeBoolean(true);
//                    String externalPath = linkedImage.getImagePath().replace("\\", "/");
//                    Codec.STRING_CODEC.encode(os, externalPath);
//                } else {
//                    os.writeBoolean(false);
//                }
//            }
//
//            @Override
//            public LinkedImage decode(DataInputStream is) throws IOException {
//                if (is.readBoolean()) {
//                    String imagePath = Codec.STRING_CODEC.decode(is);
//                    imagePath = imagePath.replace("\\", "/");
//                    return new RealLinkedImage(imagePath);
//                } else {
//                    return new EmptyLinkedImage();
//                }
//            }
//        };
//    }
//
//    boolean isReal();
//
//    /**
//     * @return The path of the image to render.
//     */
//    String getImagePath();
//
//    Node createNode();
//}
//
//class EmptyLinkedImage implements LinkedImage {
//    @Override
//    public boolean isReal() {
//        return false;
//    }
//
//    @Override
//    public String getImagePath() {
//        throw new UnsupportedOperationException("EmptyLinkedImage does not have an image path.");
//    }
//
//    @Override
//    public Node createNode() {
//        throw new UnsupportedOperationException("EmptyLinkedImage does not have an image path.");
//    }
//};
//
//class LinkedImageOps<S> extends NodeSegmentOpsBase<LinkedImage, S> {
//    public LinkedImageOps() {
//        super(new EmptyLinkedImage());
//    }
//
//    @Override
//    public int length(LinkedImage linkedImage) {
//        return linkedImage.isReal() ? 1 : 0;
//    }
//}
//
//
//class Indent
//{
//    double width = 15;
//    int level = 1;
//
//    Indent() {}
//
//    Indent( int level )
//    {
//        if ( level > 0 ) this.level = level;
//    }
//
//    Indent increase()
//    {
//        return new Indent( level+1 );
//    }
//
//    Indent decrease()
//    {
//        return new Indent( level-1 );
//    }
//
//    int getLevel() { return level; }
//
//    @Override
//    public String toString()
//    {
//        return "indent: "+ level;
//    }
//}
//
//public class RealLinkedImage implements LinkedImage {
//
//    private final String imagePath;
//
//    /**
//     * Creates a new linked image object.
//     *
//     * @param imagePath The path to the image file.
//     */
//    public RealLinkedImage(String imagePath) {
//
//        // if the image is below the current working directory,
//        // then store as relative path name.
//        String currentDir = System.getProperty("user.dir") + File.separatorChar;
//        if (imagePath.startsWith(currentDir)) {
//            imagePath = imagePath.substring(currentDir.length());
//        }
//
//        this.imagePath = imagePath;
//    }
//
//    @Override
//    public boolean isReal() {
//        return true;
//    }
//
//    @Override
//    public String getImagePath() {
//        return imagePath;
//    }
//
//    @Override
//    public String toString() {
//        return String.format("RealLinkedImage[path=%s]", imagePath);
//    }
//
//    @Override
//    public Node createNode() {
//        Image image = new Image("file:" + imagePath); // XXX: No need to create new Image objects each time -
//        // could be cached in the model layer
//        ImageView result = new ImageView(image);
//        return result;
//    }
//}