package httpgin

import (
	"log"
	"net/http"
	"time"

	"github.com/gin-gonic/gin"

	"homework9/internal/app"
)

type Server struct {
	port string
	app  *gin.Engine
}

func NewHTTPServer(port string, a app.App) *http.Server {
	gin.SetMode(gin.ReleaseMode)
	handler := gin.New()
	handler.Use(gin.Recovery())
	handler.Use(Logger)

	api := handler.Group("/api/v1")

	AppRouter(*api, a)
	s := &http.Server{Addr: port, Handler: handler}
	return s
}

func Logger(c *gin.Context) {
	t := time.Now()

	c.Next()

	latency := time.Since(t)
	status := c.Writer.Status()

	log.Println("latency", latency, "method", c.Request.Method, "path", c.Request.URL.Path, "status", status)
}

func (s *Server) Listen() error {
	return s.app.Run(s.port)
}

func (s *Server) Handler() http.Handler {
	return s.app
}
