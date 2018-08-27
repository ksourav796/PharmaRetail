/**
 * Created by azgar.h on 7/1/2017.
 */

app.controller('agentCtrl',
    function ($scope, $http, $location, $filter, Notification, ngTableParams, $timeout, $window) {
        $scope.word = /^[a-z]+[a-z0-9._]+@[a-z]+\.[a-z.]{2,5}$/;
        $scope.number = /^[0-9]/;
        $scope.firstPage = true;
        $scope.lastPage = false;
        $scope.pageNo = 1;
        $scope.prevPage = false;
        $scope.isPrev = false;
        $scope.isNext = false;
        $scope.inactiveStatus = "Active";
        $scope.companyLogoPath = "";

        $scope.today = function () {
            $scope.dt = new Date();
            $scope.dt1 = new Date();
        };
        $scope.today();
        $scope.format = 'dd/MM/yyyy';

        $scope.open1 = function () {
            $scope.popup1.opened = true;
        };

        $scope.popup1 = {
            opened: false
        };
        $scope.addNewAgentPopulate = function () {
            $scope.agentStatusText = "Active";
            $('#agent-title').text("Add Agent");
            $("#submit").text("Save");
            $("#add_new_agent_modal").modal('show');
        }
        $scope.removeAgentDetails = function () {
            $scope.agentId = "";
            $scope.AgentNameText = "";
            $scope.effectiveDateText = "";
            $scope.EmailText = "";
            $scope.MobileText = "";
            $scope.AddressText = "";
            $scope.CommissionText = "";
            $scope.GSTINText = "";
            $scope.EcommerceText = "";
            $scope.GSTINText = "";
        };
        $scope.clicked = false;
        $scope.ButtonStatus = "InActive";
        $scope.inactiveAgent = function () {
            if ($scope.clicked == false) {
                $scope.inactiveStatus = "InActive";
                $scope.ButtonStatus = "Active";
            }
            else {
                $scope.inactiveStatus = "Active";
                $scope.ButtonStatus = "InActive";
            }
            $scope.clicked = !$scope.clicked;

            $http.post('/pos' + "/getPaginatedAgentList?type=" + $scope.inactiveStatus).then(function (response) {
                var data = response.data;
                $scope.agentList = data;
                $scope.listStatus = false;

            })
        };
        $scope.getAgentList = function (type) {
            if (angular.isUndefined(type)) {
                type = "";
            }
            $http.post('/pos' + '/getPaginatedAgentList?type=' + type, {
                params: {
                    "firstPage": $scope.firstPage,
                    "lastPage": $scope.lastPage,
                    "pageNo": $scope.pageNo,
                    "prevPage": $scope.prevPage,
                    "prevPage": $scope.isPrev,
                    "nextPage": $scope.isNext
                }
            }).then(function (response) {
                    var data = response.data;
                    var i = 0;
                    $scope.agentList = data;
                    $scope.listStatus = true;
                    if (i > 0) {
                        Notification.warning({
                            message: 'Agent Became InActive',
                            positionX: 'center',
                            delay: 2000
                        });
                    }
                    $scope.first = data.first;
                    $scope.last = data.last;
                    $scope.prev = data.prev;
                    $scope.next = data.next;
                    $scope.pageNo = data.pageNo;
                }, function (error) {
                    Notification.error({
                        message: 'Something went wrong, please try again',
                        positionX: 'center',
                        delay: 2000
                    });
                }
            )
        };
        $scope.getAgentList = function (type) {
            if (angular.isUndefined(type)) {
                type = "Active";
            }
            $http.post('/pos' + '/getAgentList?type=' + type).then(function (response) {
                var data = response.data;
                console.log(data);
                $scope.agentList = data;
                $("#selectItem").modal('show');
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })
        };
        $scope.getAgentList();
        $scope.saveNewAgent = function () {
            if ($scope.AgentNameText == "" || angular.isUndefined($scope.AgentNameText)) {
                Notification.warning({message: 'Agent Name can not be empty', positionX: 'center', delay: 2000});
                return false;
            }
            if ($scope.CommissionText == "" || angular.isUndefined($scope.CommissionText)) {
                Notification.warning({message: 'Commission can not be empty', positionX: 'center', delay: 2000});
                return false;
            }
            if ($scope.dt == "" || angular.isUndefined($scope.dt)) {
                Notification.warning({message: 'Effective Date can not be empty', positionX: 'center', delay: 2000});
                return false;
            }
            // if ($scope.EmailText == "" || angular.isUndefined($scope.EmailText)) {
            //     Notification.warning({message: 'Enter a valid EmailId', positionX: 'center', delay: 2000});
            //     return false;
            // }
            if ($scope.EcommerceText == "" || angular.isUndefined($scope.EcommerceText)) {
                Notification.warning({message: 'E-Commerce can not be empty', positionX: 'center', delay: 2000});
                return false;
            }
            else {
                $scope.isDisabled = true;
                $timeout(function () {
                    $scope.isDisabled = false;
                }, 3000)
                var saveItemDetails;
                saveItemDetails = {
                    agentId: $scope.agentId,
                    agentName: $scope.AgentNameText,
                    effectiveDate: $scope.dt,
                    email: $scope.EmailText,
                    mobile: $scope.MobileText,
                    address: $scope.AddressText,
                    commission: $scope.CommissionText,
                    gstinNo: $scope.GSTINText,
                    ecommerce: $scope.EcommerceText,
                    status: $scope.agentStatusText

                };
                $http.post('/pos' + '/saveAgent', angular.toJson(saveItemDetails, "Create")).then(function (response, status, headers, config) {
                    var data = response.data;
                    if (data == "") {
                        Notification.error({
                            message: 'Agent or Gstin Already Created',
                            positionX: 'center',
                            delay: 2000
                        });
                    }
                    else {
                        $scope.removeAgentDetails();
                        $scope.getAgentList();
                        $("#add_new_agent_modal").modal('hide');
                        Notification.success({
                            message: 'Agent Created  successfully',
                            positionX: 'center',
                            delay: 2000
                        });
                        $scope.getAgentList();
                    }
                }, function (error) {
                    Notification.error({
                        message: 'Something went wrong, please try again',
                        positionX: 'center',
                        delay: 2000
                    });
                });
            }
        }
        $scope.editAgentPopulate = function (data) {
            $scope.agentObj = data;
            $scope.agentId = data.agentId;
            $scope.AgentNameText = data.agentName;
            $scope.EmailText = data.email;
            $scope.MobileText = data.mobile;
            $scope.AddressText = data.address;
            $scope.CommissionText = data.commission;
            $scope.GSTINText = data.gstinNo;
            $scope.dt = data.effectiveDate;
            $scope.EcommerceText = data.ecommerce;
            $scope.agentStatusText = data.status;
            $('#agent-title').text("Edit Agent");
            $scope.getAgentList();
            $("#submit").text("Update");
            $("#add_new_agent_modal").modal('show');
        }, function (error) {
            Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
        };
        $scope.deleteAgent = function (data) {
            bootbox.confirm({
                title: "Alert",
                message: "Do you want to Continue ?",
                buttons: {
                    confirm: {
                        label: 'OK'
                    },
                    cancel: {
                        label: 'Cancel'
                    }
                },
                callback: function (result) {
                    if (result == true) {
                        $http.post('/pos' + '/deleteAgent?agentName=' + data.agentName).then(function (response, status, headers, config) {
                            var data = response.data;
                            $scope.status = data.status;
                            if ($scope.status == "InActive") {
                                $scope.getAgentList();
                                Notification.error({
                                    message: 'It is Already in use So Status Changes to Inactive',
                                    positionX: 'center',
                                    delay: 2000
                                });
                            } else {
                                $scope.removeAgentDetails();
                                if ($scope.agentStatusText == "InActive") {
                                    $scope.getAgentList("", "InActive");
                                    Notification.success({
                                        message: 'Status has been changed to Active',
                                        positionX: 'center',
                                        delay: 2000
                                    });
                                }
                                else {
                                    $scope.getAgentList("", "");
                                    Notification.success({
                                        message: 'Status has been changed to InActive',
                                        positionX: 'center',
                                        delay: 2000
                                    });
                                }
                            }
                        }, function (error) {
                            Notification.error({
                                message: 'Something went wrong, please try again',
                                positionX: 'center',
                                delay: 2000
                            });
                        });
                    }
                }
            });
        };
        $scope.importPopup = function () {
            $("#import_agent").modal('show');
        }
    });
/* */
app.directive("preventTypingGreater", function() {
    return {
        link: function(scope, element, attributes) {
            var oldVal = null;
            element.on("keydown keyup", function(e) {
                if (Number(element.val()) > Number(attributes.max) &&
                    e.keyCode != 46 // delete
                    &&
                    e.keyCode != 8 // backspace
                ) {
                    e.preventDefault();
                    element.val(oldVal);
                } else {
                    oldVal = Number(element.val());
                }
            });
        }
    };
});