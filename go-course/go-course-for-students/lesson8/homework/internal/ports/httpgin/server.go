package httpgin

import (
	"log"
	"net/http"
	"time"

	"github.com/gin-gonic/gin"

	"homework8/internal/app"
)

type Server struct {
	port string
	app  *gin.Engine
}

func NewHTTPServer(port string, a app.App) Server {
	gin.SetMode(gin.ReleaseMode)
	s := Server{port: port, app: gin.New()}

	s.app.Use(gin.Recovery())
	s.app.Use(Logger)

	api := s.app.Group("/api/v1")

	AppRouter(*api, a)

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
