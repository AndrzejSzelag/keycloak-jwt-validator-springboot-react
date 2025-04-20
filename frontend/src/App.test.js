import { render, screen } from '@testing-library/react';
import App from './App';

test('renders JWT Token Validator', () => {
  render(<App />);
  const headingElement = screen.getByText(/JWT Token Validator/i);
  expect(headingElement).toBeInTheDocument();
});