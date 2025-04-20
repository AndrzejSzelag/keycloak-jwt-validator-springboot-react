import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import '@testing-library/jest-dom';
import JwtValidator from './JwtValidator';

// Mock fetch API
global.fetch = jest.fn();

const createMockJwt = (payload, header = { alg: 'RS256', typ: 'JWT' }) => {
    const encodeBase64 = (obj) => btoa(JSON.stringify(obj));
    return `${encodeBase64(header)}.${encodeBase64(payload)}.mockSignature123`;
};

beforeEach(() => {
    jest.clearAllMocks();
    process.env.REACT_APP_API_BASE_URL = 'http://localhost:8888';
    process.env.REACT_APP_API_PATH = '/api/v1';
    global.atob = jest.fn((str) => Buffer.from(str, 'base64').toString());
});

describe('JwtValidator Component', () => {
    test('renders correctly with empty fields', () => {
        render(<JwtValidator />);

        expect(screen.getByText('JWT Token Validator')).toBeInTheDocument();
        expect(screen.getByPlaceholderText('Paste your JWT token here...')).toBeInTheDocument();
        expect(screen.getByText('Validate JWT Token')).toBeInTheDocument();
        expect(screen.getByText('Reset')).toBeInTheDocument();
    });

    test('displays an error when no token is specified', async () => {
        render(<JwtValidator />);

        fireEvent.click(screen.getByText('Validate JWT Token'));

        expect(await screen.findByText('No JWT token provided.')).toBeInTheDocument();
    });

    test('displays an error for invalid token format', async () => {
        render(<JwtValidator />);

        fireEvent.change(screen.getByPlaceholderText('Paste your JWT token here...'), {
            target: { value: 'invalid-token' }
        });

        fireEvent.click(screen.getByText('Validate JWT Token'));

        expect(await screen.findByText('Invalid JWT format!')).toBeInTheDocument();
    });

    test('pparses and displays details of a valid token', async () => {
        const mockPayload = {
            sub: '1234567890',
            name: 'Andrzej Szelag',
            exp: Math.floor(Date.now() / 1000) + 3600 // Expires in an hour
        };

        const mockToken = createMockJwt(mockPayload);

        global.fetch.mockResolvedValueOnce({
            ok: true,
            json: async () => ({ valid: true, message: 'Token is valid!' })
        });

        render(<JwtValidator />);

        fireEvent.change(screen.getByPlaceholderText('Paste your JWT token here...'), {
            target: { value: mockToken }
        });

        fireEvent.click(screen.getByText('Validate JWT Token'));

        await waitFor(() => {
            expect(screen.getByText('Token is valid!')).toBeInTheDocument();
            expect(screen.getByText('Header')).toBeInTheDocument();
            expect(screen.getByText('Payload')).toBeInTheDocument();
            expect(screen.getByText('Signature')).toBeInTheDocument();
            expect(screen.getByText('JWT token valid until:')).toBeInTheDocument();
        });
    });

    test('displays token expiry information', async () => {
        const mockPayload = {
            sub: '1234567890',
            name: 'Andrzej Szelag',
            exp: Math.floor(Date.now() / 1000) - 3600 // Expired an hour ago
        };

        const mockToken = createMockJwt(mockPayload);

        render(<JwtValidator />);

        fireEvent.change(screen.getByPlaceholderText('Paste your JWT token here...'), {
            target: { value: mockToken }
        });

        fireEvent.click(screen.getByText('Validate JWT Token'));

        expect(await screen.findByText('JWT token has expired!', { selector: 'p' })).toBeInTheDocument();
    });

    test('handles validation error from the server', async () => {
        const mockPayload = {
            sub: '1234567890',
            name: 'Andrzej Szelag',
            exp: Math.floor(Date.now() / 1000) + 3600 // Wygasa za godzinę
        };

        const mockToken = createMockJwt(mockPayload);

        global.fetch.mockResolvedValueOnce({
            ok: false,
            json: async () => ({ error: 'Invalid signature' })
        });

        render(<JwtValidator />);

        fireEvent.change(screen.getByPlaceholderText('Paste your JWT token here...'), {
            target: { value: mockToken }
        });

        fireEvent.click(screen.getByText('Validate JWT Token'));

        await waitFor(() => {
            expect(screen.getByText('The token is not valid, and the Identity Provider - Keycloak - returned an error!')).toBeInTheDocument();
        });
    });

    test('handles server connection error', async () => {
        const mockPayload = {
            sub: '1234567890',
            name: 'Andrzej Szelag',
            exp: Math.floor(Date.now() / 1000) + 3600 // Expires in an hour
        };

        const mockToken = createMockJwt(mockPayload);

        global.fetch.mockRejectedValueOnce(new Error('Network error'));

        render(<JwtValidator />);

        fireEvent.change(screen.getByPlaceholderText('Paste your JWT token here...'), {
            target: { value: mockToken }
        });

        fireEvent.click(screen.getByText('Validate JWT Token'));

        await waitFor(() => {
            expect(screen.getByText('The token is not valid, and the Identity Provider - Keycloak - returned an error!')).toBeInTheDocument();
        });
    });

    test('resets the status when the Reset button is clicked', async () => {
        const mockPayload = {
            sub: '1234567890',
            name: 'Andrzej Szelag',
            exp: Math.floor(Date.now() / 1000) + 3600
        };

        const mockToken = createMockJwt(mockPayload);

        global.fetch.mockResolvedValueOnce({
            ok: true,
            json: async () => ({ valid: true, message: 'Token is valid!' })
        });

        render(<JwtValidator />);

        fireEvent.change(screen.getByPlaceholderText('Paste your JWT token here...'), {
            target: { value: mockToken }
        });

        fireEvent.click(screen.getByText('Validate JWT Token'));

        await waitFor(() => {
            expect(screen.getByText('Token is valid!')).toBeInTheDocument();
        });

        fireEvent.click(screen.getByText('Reset'));

        expect(screen.queryByText('Token is valid!')).not.toBeInTheDocument();
        expect(screen.queryByText('Header')).not.toBeInTheDocument();
        expect(screen.getByPlaceholderText('Paste your JWT token here...').value).toBe('');
    });
});