import React, { useState } from 'react';
import '../index.css';

const JwtValidator = () => {
  const [token, setToken] = useState('');
  const [result, setResult] = useState(null);
  const [error, setError] = useState(null);
  const [tokenParts, setTokenParts] = useState(null);
  const shouldShowTokenDetails =
    tokenParts && !(error && error.localValidation && error.error === 'Server connection error!');
  const apiUrl = `${process.env.REACT_APP_API_BASE_URL}${process.env.REACT_APP_API_PATH}/jwt/validate`;

  const parseJwt = (token) => {
    try {
      const parts = token.split('.');
      if (parts.length !== 3) {
        return null;
      }

      return {
        rawToken: token,
        header: JSON.parse(atob(parts[0])),
        payload: JSON.parse(atob(parts[1])),
        signature: parts[2] // Signature is encoded, so we display it in RAW form
      };
    } catch (e) {
      return null;
    }
  };

  const handleCombinedAction = async () => {
    setResult(null);
    setError(null);

    if (!token) {
      setError({ error: 'No JWT token provided.' });
      setTokenParts(null);
      return;
    }

    // First analyse the token locally
    try {
      const parsedToken = parseJwt(token);
      if (!parsedToken) {
        setError({ error: 'Invalid JWT format!' });
        setTokenParts(null);
        return;
      }

      setTokenParts(parsedToken);

      // Check locally if the token has expired
      if (parsedToken.payload.exp) {
        const expirationDate = new Date(parsedToken.payload.exp * 1000);
        const now = new Date();

        if (now > expirationDate) {
          setError({ error: 'JWT token has expired!' });
          return; // Exit early without setting result
        }
      }

      // Then verify the token on the server (full validation)
      try {
        const response = await fetch(apiUrl, {
          method: 'GET',
          headers: {
            Authorization: `Bearer ${token}`
          }
        });

        if (!response.ok) {
          const errorData = await response.json();
          setError({ ...errorData, localValidation: true });
        } else {
          const data = await response.json();
          setResult({ ...data, valid: true, message: 'Token is valid!' });
          setError(null);
        }
      } catch (err) {
        setError({ error: 'Server connection error!', localValidation: true });
      }

    } catch (e) {
      setError({ error: 'Invalid JWT token format!' });
      setResult(null);
      setTokenParts(null);
    }
  };

  const handleReset = () => {
    setToken('');
    setResult(null);
    setError(null);
    setTokenParts(null);
  };

  return (
    <div className="jwt-validator">
      <h2>JWT Token Validator</h2>
      <div className="jwt-input">
        <textarea
          value={token}
          onChange={(e) => setToken(e.target.value)}
          placeholder="Paste your JWT token here..."
          rows={5}
          className="jwt-textarea"
        />
      </div>

      <div className="jwt-buttons">
        <button className="jwt-button analyse" onClick={handleCombinedAction}>Validate JWT Token</button>
        <button className="jwt-button reset" onClick={handleReset}>Reset</button>
      </div>

      {error && (
        <div className="jwt-error">
          <h3>Validation Error</h3>
          <p>
            {error.localValidation
              ? 'The token is not valid, and the Identity Provider - Keycloak - returned an error!'
              : error.error}
          </p>
        </div>
      )}

      {result && (
        <div className={`jwt-result ${result.valid ? 'valid' : 'invalid'}`}>
          <h3>Validation Result</h3>
          <p>{result.message}</p>
        </div>
      )}

      {shouldShowTokenDetails && (
        <div className="jwt-details">
          <h2>Token Details</h2>
          <div className="jwt-columns">
            {/* First column - Header, Signature and Date */}
            <div className="jwt-column">
              <div className="jwt-section">
                <h4>Header</h4>
                <pre>{JSON.stringify(tokenParts.header, null, 2)}</pre>
              </div>
              <div className="jwt-section">
                <h4>Signature</h4>
                <pre>{tokenParts.signature}</pre>
              </div>
              <div className="jwt-section">
                {tokenParts.payload.exp && (
                  <div className="jwt-expiration">
                    <h2 className={`jwt-expiration-status ${new Date() > new Date(tokenParts.payload.exp * 1000) ? 'expired' : 'valid'}`}>
                      {new Date() > new Date(tokenParts.payload.exp * 1000)
                        ? "JWT token has expired!"
                        : "JWT token valid until:"}
                    </h2>
                    <h2>Expiration date: {new Date(tokenParts.payload.exp * 1000).toLocaleString()}</h2>
                  </div>
                )}
              </div>
            </div>
            {/* Second column - Payload */}
            <div className="jwt-column">
              <div className="jwt-section">
                <h4>Payload</h4>
                <pre>{JSON.stringify(tokenParts.payload, null, 2)}</pre>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default JwtValidator;