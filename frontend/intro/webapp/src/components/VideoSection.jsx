import React from "react";

const VideoSection = () => {
  return (
    <div className="container mt-5">
      <div
        className="text-center mb-3 overviewHeading"
        style={{
          fontSize: "36px",
          fontWeight: 600,
          fontFamily: "Inter-Bold",
          letterSpacing: "1px",
        }}
      >
        A Quick Overview of the ULCA
      </div>
      <div
        className="display-6 text-center"
        style={{
          fontFamily: "OS-Regular",
          fontSize: "16px",
          fontWeight: 400,
          lineHeight: "24px",
        }}
      >
        Get ready for a swift introduction to ULCA! Our video provides a quick
        overview, highlighting the key features
        <br />
        and benefits of ULCA's platform.
      </div>
      <div className="video-overview">
        <p>Video Magic Coming Soon Here!</p>
      </div>
    </div>
  );
};

export default VideoSection;
