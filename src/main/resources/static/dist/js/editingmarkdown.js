


define(function () {


    var MdEditor={


        format:{

            undo: function undo(editor) {
                editor.undo();
            },

            redo: function redo(editor) {
                editor.redo();
            },


            //粗体
            setBold: function setBold(editor) {
                editor.replaceSelection(`**${editor.getSelection()}**`);
                var cursor = editor.getCursor();
                editor.setCursor({
                    line: cursor.line,
                    ch: cursor.ch - 2
                });
                editor.focus();
            },

            //斜体
            setItalic: function setItalic(editor) {
                editor.replaceSelection(`*${editor.getSelection()}*`);
                var cursor = editor.getCursor();
                editor.setCursor({
                    line: cursor.line,
                    ch: cursor.ch - 1
                });
                editor.focus();
            },

            setBlockQuote: function setBlockQuote(editor) {
                var cursorLine = editor.getCursor().line;
                editor.replaceRange('> ', {
                    line: cursorLine,
                    ch: 0
                }, {
                    line: cursorLine,
                    ch: 0
                });
                editor.focus();
            },

            setHeader: function setHeader(editor, level) {
                var cursorLine = editor.getCursor().line;
                switch (level) {
                    case 1:
                        editor.replaceRange('# ', {
                            line: cursorLine,
                            ch: 0
                        }, {
                            line: cursorLine,
                            ch: 0
                        });
                        break;
                    case 2:
                        editor.replaceRange('## ', {
                            line: cursorLine,
                            ch: 0
                        }, {
                            line: cursorLine,
                            ch: 0
                        });
                        break;
                    case 3:
                        editor.replaceRange('### ', {
                            line: cursorLine,
                            ch: 0
                        }, {
                            line: cursorLine,
                            ch: 0
                        });
                        break;
                    case 4:
                        editor.replaceRange('#### ', {
                            line: cursorLine,
                            ch: 0
                        }, {
                            line: cursorLine,
                            ch: 0
                        });
                        break;
                    case 5:
                        editor.replaceRange('##### ', {
                            line: cursorLine,
                            ch: 0
                        }, {
                            line: cursorLine,
                            ch: 0
                        });
                        break;
                    default:
                        break;
                }
                editor.focus();
            },


            setLink: function setLink(editor) {
                editor.replaceSelection(`[](https://)`);
                var cursor = editor.getCursor();
                editor.setCursor({
                    line: cursor.line,
                    ch: cursor.ch - 11
                });
                editor.focus();
            },

            setImage: function setImage(editor) {
                editor.replaceSelection(`![]()`);
                var cursor = editor.getCursor();
                editor.setCursor({
                    line: cursor.line,
                    ch: cursor.ch - 3
                });
                editor.focus();
            },


            setInlineCode: function setInlineCode(editor) {
                editor.replaceSelection(`\`${editor.getSelection()}\``);
                var cursor = editor.getCursor();
                editor.setCursor({
                    line: cursor.line,
                    ch: cursor.ch - 1
                });
                editor.focus();
            },


            uploadImage:function (editor) {
                var input_file=$('<input type="file" name="file" accept="image/jpg,image/jpeg,image/png,image/gif">');
                input_file.on('change',function () {
                    var file=input_file[0].files[0];
                    if(!file){
                        return false;
                    }
                    var form=new FormData();
                    form.append("file",file);


                    $.ajax({
                        url: _MTONS.BASE_PATH + "/post/upload",
                        data: form,
                        type: "POST",
                        cache: false, //上传文件无需缓存

                        /*
                           请求--->响应
                           请求的content-type告诉服务器，实际发送的数据类型。

                           响应--->客户端
                           服务器告诉浏览器，返回的数据类型，浏览器会根据该类型

                            processData
                            会将数据转换为默认的内容类型，默认的contentType是application/x-www-form-urlencoded。
                            如果不希望转换，设置为false。要对该参数进行序列化处理。设置为false。

                            contentType，默认是application/x-www-form-urlencoded。设置为false。
                        */

                        processData: false,
                        contentType: false,

                        //请求成功回调函数
                        success:function (result) {
                            if(result.status===200){
                                var image = `![` + result.name + `](` + _MTONS.BASE_PATH + result.path + `)`;
                                editor.replaceSelection(image);
                                var cursor = editor.getCursor();
                                editor.setCursor({
                                    line: cursor.line,
                                    ch: cursor.ch - 3
                                });
                                editor.focus();
                            }else {
                                layer.alert(result.message);
                            }
                        }
                    });

                });

                input_file.click();
            },



            setPreMode: function setPreMode(element, mode, editor) {
                //去除active，为选中的元素添加active。
                $('button[event=premode].active').removeClass('active');
                element.addClass('active');
                //为editor-container添加一个mode,eidtMode，liveMode，previewMode
                $('.editor-container').removeClass('liveMode editMode previewMode').addClass(mode);
            },


            fullscreen: function (editor) {
                var $btn = $('button[event=fullscreen]');
                $btn.toggleClass('active');
                $('.md-editor').toggleClass('fullscreen');
                var height = $(window).height();
                if ($btn.hasClass('active')) {
                    editor.setSize('auto', height + 'px');
                } else {
                    editor.setSize('auto', '450px');
                }
            }
        },





        initEditor:function () {

            /*根据一个DOM元素构建一个编辑器*/
            var editor = CodeMirror.fromTextArea(document.getElementById("content"), {
                mode: 'markdown',     // Markdown
                lineNumbers: true,     // 显示行数
                indentUnit: 4,         // 缩进多少格子，默认为2
                tabSize: 4,            //tab空格的宽度
                autoCloseBrackets: true,
                matchBrackets: true,   // 括号匹配
                lineWrapping: true,    // 长的行文字自动换行
                highlightFormatting: true,
                theme: 'idea',          //
                keyMap: 'sublime',      //配置快捷键
                smartIndent:false,
                extraKeys: {"Enter": "newlineAndIndentContinueMarkdownList"}
            });

            editor.setSize('auto', '550px');

            editor.on('change', function (editor) {
                //textarea添加内容。
                var $content = $('#content');
                $content.text(editor.getValue());
                $('.editor-preview').html(marked(editor.getValue()));
            });

            $('.editor-preview').html(marked($('#content').text()));


            // Toolbar click
            $('div.editor-toolbar').on('click', 'button[event]', function () {
                var that = $(this);
                var event = that.attr('event');

                switch (event) {
                    case 'undo':
                        MdEditor.format.undo(editor);
                        break;
                    case 'redo':
                        MdEditor.format.redo(editor);
                        break;
                    case 'bold':
                        MdEditor.format.setBold(editor);
                        break;
                    case 'italic':
                        MdEditor.format.setItalic(editor);
                        break;
                    case 'blockquote':
                        MdEditor.format.setBlockQuote(editor);
                        break;
                    case 'h1':
                        MdEditor.format.setHeader(editor, 1);
                        break;
                    case 'h2':
                        MdEditor.format.setHeader(editor, 2);
                        break;
                    case 'h3':
                        MdEditor.format.setHeader(editor, 3);
                        break;
                    case 'h4':
                        MdEditor.format.setHeader(editor, 4);
                        break;
                    case 'h5':
                        MdEditor.format.setHeader(editor, 5);
                        break;
                    case 'link':
                        MdEditor.format.setLink(editor);
                        break;
                    case 'image':
                        MdEditor.format.setImage(editor);
                        break;
                    case 'inlinecode':
                        MdEditor.format.setInlineCode(editor);
                        break;
                    case 'uploadimage':
                        MdEditor.format.uploadImage(editor);
                        break;
                    case 'premode':
                        var mode = that.data('value');
                        MdEditor.format.setPreMode(that, mode, editor);
                        break;
                    case 'fullscreen':
                        MdEditor.format.fullscreen(editor);
                        break;
                    default:
                        break;
                }

            });
        }
    };

    return MdEditor;
});


