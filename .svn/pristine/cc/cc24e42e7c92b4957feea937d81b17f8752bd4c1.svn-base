app.controller('editTemplateCtrl', function ($scope, $http, $routeParams, editTemplateService, keyPressFactory) {
    window.$scope = $scope;
    $scope.isTrue=false;
    $scope.hiposServerURL =  "/hipos/";
    $scope.customer = 1;
    $scope.formsetupId=$routeParams.id;
    $scope.borderVal ="border";
    var formsetupId=$scope.formsetupId;
        var count = 0;
        var report_coordinate = {};
        general = [];
        $scope.shortCutRestrict=keyPressFactory.shortCutRestrict();
        $(function () {
            $(".draggable").draggable({
                snap: ".ui-widget-header",
                snapMode: "outer",
                containment: ".ui-widget-header",
                refreshPositions: true,
                disabled: true,
                start: function (event, ui) {
                    var level = 100;
                    $(this).css("z-index", level + 1);
                },
                stop: function (event, ui) {
                    var drag_id = $(this).closest('div').attr('id');
                    var left = ui.position.left + "px";
                    var top = ui.position.top + "px";
                    var position = $(this).css("position");
                    var width = $(this).css("width");
                    var height = $(this).css("height");
                    var i = count;
                    var flag = 0;
                    item = {};
                    item["drag_id"] = drag_id,
                    item["left"] = left,
                    item["top"] = top,
                    item["width"] = width,
                    item["height"] = height,
                    item["position"] = position;
                    if ((general.length) == 0) {
                        general.push(item);
                    } else {
                        for (i = 0; i < general.length; i++) {
                            if ((general[i].drag_id) == drag_id) {
                                general[i].left = left;
                                general[i].top = top;
                                general[i].width = width;
                                general[i].height = height;
                                general[i].position = position;
                                flag = 1;
                                break;
                            }
                        }
                        if (flag == 0) {
                            general.push(item);
                        }
                    }
                    jsonString = JSON.stringify(general);
                    positions = JSON.parse(jsonString);
                }
            });

            $scope.editForm = function () {
                $http.post($scope.hiposServerURL + $scope.customer + '/getEditFormSetupElements/'+formsetupId).then(function (response, status, headers, config) {

                    var data = response.data;
                    $scope.editFormdragable = data;
                    if(data.length !== 0)
                    $scope.isTrue=true;

                    console.log(data);
                    $scope.itemProductList = data;
                    $scope.productitem = data.itemName;
                    $("#selectProduct").modal('show');

                })

            };


            $(".draggableitem").draggable({
                snap: ".ui-widget-header",
                snapMode: "outer",
                containment: ".ui-widget-header",
                refreshPositions: true,
                disabled: true,
                start: function (event, ui) {
                    var level = 100;
                    $(this).css("z-index", level + 1);
                },
                stop: function (event, ui) {
                    var drag_id = $(this).closest('div').attr('id');
                    var left = ui.position.left + "px";
                    var top = ui.position.top + "px";
                    var position = "absolute";
                    var width = $(this).css("width");
                    var height = $(this).css("height");
                    var i = count;
                    var flag = 0;
                    item = {};
                    item["drag_id"] = drag_id,
                        item["left"] = left,
                        item["top"] = top,
                        item["width"] = width,
                        item["height"] = height,
                        item["position"] = position;
                    if ((general.length) == 0) {
                        general.push(item);
                    } else {
                        for (i = 0; i < general.length; i++) {
                            if ((general[i].drag_id) == drag_id) {
                                general[i].left = left;
                                general[i].top = top;
                                general[i].width = width;
                                general[i].height = height;
                                general[i].position = position;
                                flag = 1;
                                break;
                            }
                        }
                        if (flag == 0) {
                            general.push(item);
                        }
                    }
                    jsonString = JSON.stringify(general);
                    positions = JSON.parse(jsonString);
                }
            });
   });
var ch1;
        $(document).ready(function (e) {
            $scope.editForm();
            setTimeout(function(){
                $('.draggable').each(function (index) {
                    var $el = $(this);
                    $el.draggable({containment: $el.closest('.ui-widget-header')});
                    var drag_id = $(this).closest('div').attr('id');
                    if ($scope.editFormdragable[index] !== undefined) {
                        var left = $scope.editFormdragable[index].left;
                        var top = $scope.editFormdragable[index].top;
                        var position = $scope.editFormdragable[index].position;
                        var width = $scope.editFormdragable[index].width;
                        var height = $scope.editFormdragable[index].height;
                        ch1 = index;
                        ch1++;
                    }
                    else {
                        var left = $(this).css("left");
                        var top = $(this).css("top");
                        var position = $(this).css("position");
                        var width = $(this).css("width");
                        var height = $(this).css("height");
                    }

                    var i = count;
                    var flag = 0;
                    item = {};
                    item["drag_id"] = drag_id,
                    item["left"] = left,
                    item["top"] = top,
                    item["width"] = width,
                    item["height"] = height,
                    item["position"] = position;
                    if ((general.length) == 0) {
                        general.push(item);
                    } else {
                        for (i = 0; i < general.length; i++) {
                            if ((general[i].drag_id) == drag_id) {
                                general[i].left = left;
                                general[i].top = top;
                                general[i].width = width;
                                general[i].height = height;
                                general[i].position = position;
                                flag = 1;
                                break;
                            }
                        }
                        if (flag == 0) {
                            general.push(item);
                        }
                    }
                    jsonString = JSON.stringify(general);
                    positions = JSON.parse(jsonString);
                    //alert('FUNC 2   '+jsonString);
                });

                for (var l = 0; l < general.length; l++) {
                    if (general[l].position == "relative") {
                        $("#"+general[l].drag_id).hide();
                        $('#hideSelectBoxId')
                            .append($("<option></option>")
                                .attr("value",general[l].drag_id)
                                .text($('#'+general[l].drag_id).children('p').text()));
                    }
                }

            $('.draggableitem').each(function (index) {
                var $el = $(this);
                $el.draggable({containment: $el.closest('.ui-widget-header')});
                var drag_id = $(this).closest('div').attr('id');
                if ($scope.editFormdragable[index] !== undefined) {
                    var left = $scope.editFormdragable[ch1].left;
                    var top = $scope.editFormdragable[ch1].top;
                    var position = $scope.editFormdragable[ch1].position;
                    var width = $scope.editFormdragable[ch1].width;
                    var height = $scope.editFormdragable[ch1].height;
                }
                else {
                    var left = $(this).css("left");
                    var top = $(this).css("top");
                    var position = $(this).css("position");
                    var width = $(this).css("width");
                    var height = $(this).css("height");
                }

                var i = count;
                var flag = 0;
                item = {};
                item["drag_id"] = drag_id,
                    item["left"] = left,
                    item["top"] = top,
                    item["width"] = width,
                    item["height"] = height,
                    item["position"] = position;
                if ((general.length) == 0) {
                    general.push(item);
                } else {
                    for (i = 0; i < general.length; i++) {
                        if ((general[i].drag_id) == drag_id) {
                            general[i].left = left;
                            general[i].top = top;
                            general[i].width = width;
                            general[i].height = height;
                            general[i].position = position;
                            flag = 1;
                            break;
                        }
                    }
                    if (flag == 0) {
                        general.push(item);
                    }
                }
                jsonString = JSON.stringify(general);
                positions = JSON.parse(jsonString);
                ch1++;
            });


                var item = {};
                if ($scope.editFormdragable[$scope.editFormdragable.length-1].width == "true") {
                    $scope.borderVal = "border";
                    item["width"] = true;
                    item["drag_id"] = "borderVal";
                }
                else if ($scope.editFormdragable[$scope.editFormdragable.length-1].width == "false") {
                    $scope.borderVal = "noBorder";
                    item["width"] = false;
                    item["drag_id"] = "borderVal";
                }
                else {
                    if ($scope.borderVal === "noBorder") {
                        item["width"] = true;
                        item["drag_id"] = "borderVal";
                    }
                    else if($scope.borderVal == "border") {
                        item["width"] = false;
                        item["drag_id"] = "borderVal";
                    }
                }

            }, 2000);

                $(".resizeable").resizable({
                    containment: "document",
                    minHeight: 20,
                    minWidth: 30,
                    handles: 'ne, se, sw, nw, e, w, s'
                });

        /*** Comment below lines on normal page, Uncomment in edit page  ***/
        $(".draggable").draggable("option", "disabled", false);
         $(".draggableitem").draggable("option", "disabled", false);
        $(".resizeable").resizable("option", "disabled", false);
    });


        $("#head_content").resizable({
            containment: "document",
            handles: 'n, s'
        });
        $(".resizeable").resizable({
            refreshPositions: true,
            disabled: false,
            stop: function (event, ui) {
                var resize_id = $(this).closest('div').attr('id');
                var position = $(this).css("position");
                var left = ui.position.left + "px";
                var top = ui.position.top + "px";
                var width = ui.size.width + "px";
                var height = ui.size.height + "px";
                var i = count;
                var rflag = 0;
                item = {};
                item["drag_id"] = resize_id,
                    item["left"] = left,
                    item["top"] = top,
                    item["width"] = width,
                    item["height"] = height,
                    item["position"] = position;

                if ((general.length) == 0) {
                    general.push(item);
                } else {
                    for (i = 0; i < general.length; i++) {
                        if ((general[i].drag_id) == resize_id) {
                            general[i].left = left;
                            general[i].top = top;
                            general[i].width = width;
                            general[i].height = height;
                            general[i].position = position;
                            rflag = 1;
                            break;
                        }
                    }
                    if (rflag == 0) {
                        general.push(item);
                    }
                }
                jsonString = JSON.stringify(general);
                positions = JSON.parse(jsonString);
                console.log("RESIZE :: "+jsonString);
            }
        });

    var saveFormSetup = [];
        /*method for SAVE button*/

        $scope.dragClose = function (event){
            $('#'+event.target.parentNode.id).css('position','relative');
            $('#'+event.target.parentNode.id).hide();
            for (var m = 0; m < general.length; m++) {
                if (general[m].drag_id == event.target.parentNode.id) {
                    general[m].position = "relative";
                    break;
                }
            }
           var cur_val = $('#'+event.target.parentNode.id).children('p').text();
            $('#hideSelectBoxId')
                .append($("<option></option>")
                    .attr("value",$('#'+event.target.parentNode.id).attr('id'))
                    .text(cur_val));
        }

    $scope.restoreDrag = function () {
          cur = $('#hideSelectBoxId').val();
          $('#'+cur).css('position','absolute');
        for (var m = 0; m < general.length; m++) {
            if (general[m].drag_id == cur) {
                general[m].position = "absolute";
                break;
            }
        }
          $('#'+cur).show();
          $("#hideSelectBoxId option[value="+cur+"]").remove();
    }

    $scope.saveTemplate = function(buttonElement) {
            var r = confirm("Do you want to save the template ?");
            if (r == true) {
                $scope.isTrue=true;

                $('#saveTemplateBtn').prop('disabled', true);
                var formSetupName = "editTemplate";
                var jsonString = JSON.stringify(general);

                 if (jsonString != null && general.length != 0) {

                     var item = {};
                     if ($scope.borderVal === "noBorder") {
                         item["width"] = false;
                         item["drag_id"] = "borderVal";
                     }
                     else if($scope.borderVal == "border")
                     {
                         item["width"] = true;
                         item["drag_id"] = "borderVal";
                     }
                     general.push(item);

                     for (var i = 0; i < general.length; i++) {
                         $scope.formSetupName = formsetupId;
                         saveFormSetup.push({
                             "drag_id": general[i].drag_id,
                             "left": general[i].left,
                             "top": general[i].top,
                             "width":general[i].width,
                             "height":general[i].height,
                             "position": general[i].position,
                             "form_id" : $scope.formsetupId
                         });
                     }
                     var saveFormSetupList ={
                         formSetupTemplateElementList:saveFormSetup
                     };
                     $http.post($scope.hiposServerURL + "/" + $scope.customer +  '/updateFormSetupElements/'+formsetupId, general).then(function (response, status, headers, config) {
                         var data = response.data;

                     }).then(function onSuccess(data) {

                         var data = response.data;
                         var status = response.status;
                         var statusText = response.statusText;
                         var headers = response.headers;
                         var config = response.config;
                     }), function onError(response) {

                         var data = response.data;
                         var status = response.status;
                         var statusText = response.statusText;
                         var headers = response.headers;
                         var config = response.config;
                     };
                 }
                 window.location.href='#!/formsetup';
            } else {
                $('#saveTemplateBtn').prop('disabled', false);
            }

        };



    /*method for RESET button*/
        $scope.resetTemplate = function() {
                $('#saveTemplateBtn').prop('disabled', false);
                 var formSetupName = $('#formSetupName').val();
                 var r = confirm("Are you want to reset!");
                 if (r == true) {
                     editTemplateService.resetTemplate(formSetupName,function (response) {
                         $scope.isTrue=false;
                         reset();
                         for (var m = 0; m < general.length; m++) {
                             if (general[m].position == "relative") {
                                 general[m].position = "absolute";
                                 $("#"+general[m].drag_id).show();
                                 $('#'+general[m].drag_id).css('position','absolute');
                                 $("#hideSelectBoxId option[value="+general[m].drag_id+"]").remove()
                             }
                         }
                     });
                 }
        };

      function  reset(){
    setTimeout(function(){
        $('.draggable').each(function (index) {
            var $el = $(this);
            $el.draggable({containment: $el.closest('.ui-widget-header')});
            var drag_id = $(this).closest('div').attr('id');
                var left = $(this).css("left");
                var top = $(this).css("top");
                var position = $(this).css("position");
                var width = $(this).css("width");
                var height = $(this).css("height");

            var i = count;
            var flag = 0;
            item = {};
            item["drag_id"] = drag_id,
                item["left"] = left,
                item["top"] = top,
                item["width"] = width,
                item["height"] = height,
                item["position"] = position;
            if ((general.length) == 0) {
                general.push(item);
            } else {
                for (i = 0; i < general.length; i++) {
                    if ((general[i].drag_id) == drag_id) {
                        general[i].left = left;
                        general[i].top = top;
                        general[i].width = width;
                        general[i].height = height;
                        general[i].position = position;
                        flag = 1;
                        break;
                    }
                }
                if (flag == 0) {
                    general.push(item);
                }
            }
            jsonString = JSON.stringify(general);
            positions = JSON.parse(jsonString);
        });

          $('.draggableitem').each(function () {
              var $el = $(this);
              $el.draggable({containment: $el.closest('.ui-widget-header')});
              var drag_id = $(this).closest('div').attr('id');
                  var left = $(this).css("left");
                  var top = $(this).css("top");
                  var position = $(this).css("position");
                  var width = $(this).css("width");
                  var height = $(this).css("height");

              var i = count;
              var flag = 0;
              item = {};
              item["drag_id"] = drag_id,
                  item["left"] = left,
                  item["top"] = top,
                  item["width"] = width,
                  item["height"] = height,
                  item["position"] = position;
              if ((general.length) == 0) {
                  general.push(item);
              } else {
                  for (i = 0; i < general.length; i++) {
                      if ((general[i].drag_id) == drag_id) {
                          general[i].left = left;
                          general[i].top = top;
                          general[i].width = width;
                          general[i].height = height;
                          general[i].position = position;
                          flag = 1;
                          break;
                      }
                  }
                  if (flag == 0) {
                      general.push(item);
                  }
              }
              jsonString = JSON.stringify(general);
              positions = JSON.parse(jsonString);
          });
    }, 2000);
    }

        $scope.showelements = function(id) {
            if(id == "snoVal"){
                $("#snoVal").removeClass('hidden')
            }  else if(id == "itemNameValue") {
                $("#itemNameValue").removeClass('hidden')
            } else if(id == "desValue") {
                $("#desValue").removeClass('hidden')
            } else if(id == "itemHsnCodeValue") {
                $("#itemHsnCodeValue").removeClass('hidden')
            } else if(id == "quantityVal") {
                $("#quantityVal").removeClass('hidden')
            } else if(id == "itemRateValue") {
                $("#itemRateValue").removeClass('hidden')
            } else if(id == "AmountVal") {
                $("#AmountVal").removeClass('hidden')
            } else if(id == "itemDiscountVal") {
                $("#itemDiscountVal").removeClass('hidden')
            } else if(id == "taxVal") {
                $("#taxVal").removeClass('hidden')
            } else if(id == "taxAmountVal") {
                $("#taxAmountVal").removeClass('hidden')
            } else if(id == "CgstVal"){
                $("#CgstVal").removeClass('hidden')
            } else if(id == "IgstVal"){
                $("#IgstVal").removeClass('hidden')
            } else if(id == "SgstVal"){
                $("#SgstVal").removeClass('hidden')
            }else if(id == "cessVal"){
                $("#cessVal").removeClass('hidden')
            }else {
                $("#returnQuantityVal").removeClass('hidden')
            }
        };

});












