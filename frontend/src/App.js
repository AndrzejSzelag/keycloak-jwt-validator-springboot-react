import './index.css';
import React from 'react';
import { useEffect } from 'react';
import JwtValidator from './components/JwtValidator';

function App() {
  useEffect(() => {
    document.title = process.env.REACT_APP_TITLE;
  }, []);
  return (
    <div className="App">
      <JwtValidator />
    </div>
  );
}

export default App;