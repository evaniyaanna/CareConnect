import {
    useState
} from "react";

import api from "../api/axios";

function ChangePassword() {

    const [
        currentPassword,
        setCurrentPassword
    ] = useState("");


    const [
        newPassword,
        setNewPassword
    ] = useState("");


    const [
        confirmPassword,
        setConfirmPassword
    ] = useState("");


    const [error, setError] =
        useState("");


    const [message, setMessage] =
        useState("");


    const handleSubmit = async (event) => {

        event.preventDefault();

        setError("");

        setMessage("");


        if (
            newPassword !==
            confirmPassword
        ) {

            setError(
                "New passwords do not match."
            );

            return;
        }


        try {

            const response =
                await api.put(
                    "/change-password",
                    {
                        currentPassword:
                            currentPassword,

                        newPassword:
                            newPassword
                    }
                );


            setMessage(
                response.data.message
                ||
                "Password updated successfully."
            );


            setCurrentPassword("");

            setNewPassword("");

            setConfirmPassword("");

        } catch (error) {

            setError(
                error.response?.data?.message
                ||
                "Unable to update password."
            );

        }

    };


    return (

        <div className="row justify-content-center">

            <div className="col-md-6">

                <h2 className="mb-4">
                    Change Password
                </h2>


                {message && (

                    <div className="alert alert-success">

                        {message}

                    </div>

                )}


                {error && (

                    <div className="alert alert-danger">

                        {error}

                    </div>

                )}


                <form
                    onSubmit={handleSubmit}
                >

                    <div className="form-group">

                        <label>
                            Current Password
                        </label>

                        <input
                            type="password"
                            className="form-control"
                            value={
                                currentPassword
                            }
                            onChange={
                                event =>
                                    setCurrentPassword(
                                        event.target.value
                                    )
                            }
                            required
                        />

                    </div>


                    <div className="form-group">

                        <label>
                            New Password
                        </label>

                        <input
                            type="password"
                            className="form-control"
                            value={
                                newPassword
                            }
                            onChange={
                                event =>
                                    setNewPassword(
                                        event.target.value
                                    )
                            }
                            required
                        />

                    </div>


                    <div className="form-group">

                        <label>
                            Confirm New Password
                        </label>

                        <input
                            type="password"
                            className="form-control"
                            value={
                                confirmPassword
                            }
                            onChange={
                                event =>
                                    setConfirmPassword(
                                        event.target.value
                                    )
                            }
                            required
                        />

                    </div>


                    <button
                        type="submit"
                        className="btn btn-primary"
                    >
                        Change Password
                    </button>

                </form>

            </div>

        </div>
    );
}


export default ChangePassword;