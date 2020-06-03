window.Signup_motor = {
    API_URL: "http://localhost:8083/createMotorcycle",

    singupMotor: function (profile) {

        let userId = profile.id;
        let requestBody = {
            userId : userId,
            modelMotor: $("#example-motormodel").val(),
            userName: $("#example-motorname").val()
        };


        $.ajax({
            url: Signup_motor.API_URL + "/motorcycle",
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify(requestBody)
        }).done(function () {
            window.location.replace("/newsfeed")
        })
    },

    bindEvents: function () {
        $("#btn-motorSign").click(function (event) {
            event.preventDefault();

            Signup_motor.singupMotor();
        });
    }

};
Signup_motor.bindEvents();