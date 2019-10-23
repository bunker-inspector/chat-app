defmodule ChatServerWeb.Router do
  use ChatServerWeb, :router

  pipeline :browser do
    plug :accepts, ["html"]
    plug :fetch_session
    plug :fetch_flash
    plug :protect_from_forgery
    plug :put_secure_browser_headers
  end

  pipeline :api do
    plug :accepts, ["json"]
  end

  scope "/", ChatServerWeb do
    pipe_through :browser
    get "/", PageController, :index
  end

  scope "/", ChatServerWeb do
    get "/:short_url", RedirectController, :redirect_to_long
  end
end
