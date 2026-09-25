import {
    Outlet,
    useLocation
} from "react-router-dom";

import Header
    from "./components/Header";

import Navigation
    from "./components/Navigation";

import Footer
    from "./components/Footer";

import AutoLogin
    from "./components/AutoLogin";


function App() {

    const location =
        useLocation();


    const isLandingPage =
        location.pathname === "/";


    return (

        <>

            {/* DO NOT CHANGE AUTO LOGIN */}

            <AutoLogin />


            {isLandingPage ? (

                <Outlet />

            ) : (

                <div className="d-flex flex-column min-vh-100">

                    <Header />

                    <Navigation />


                    <main className="flex-grow-1">

                        <Outlet />

                    </main>


                    <Footer />

                </div>

            )}

        </>

    );

}


export default App;