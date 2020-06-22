window.Signup_motor = {
    API_URL: "http://localhost:8083/createMotorcycle",

    singupMotor: function () {

        let userId = localStorage.getItem("user");
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
            $(".signup-text").replaceWith('<p class="signup-text">Congratulations! You have just created new user account. Please LogIn ' +
                '<a style="color: red; size: 10px" href="/login">here</a></p>');

            localStorage.removeItem("user");
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